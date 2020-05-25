package com.karonda.mysqldatarestore;

import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

@Component
public class RestoreRunner implements CommandLineRunner {

    private final String filePath = "D:\\mysql-bin.******"; // binlog 路径
    private final String beginTime = "2020-05-25 08:10:23"; // 删除数据开始时间
    private final String endTime = "2020-05-25 08:10:26"; // 删除数据结束时间
    private final String databaseName = "test_db"; // 数据库名
    private final String tableName = "test_talbe"; // 数据表名

    private static Logger logger = LoggerFactory.getLogger("");

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private long tableId;
    private int count;


    @Override
    public void run(String... args) throws Exception {

        long beginTimestamp = simpleDateFormat.parse(beginTime).getTime();
        long endTimestamp = simpleDateFormat.parse(endTime).getTime();

        File binlogFile = new File(filePath);
        EventDeserializer eventDeserializer = new EventDeserializer();
//        eventDeserializer.setCompatibilityMode(
//                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
//                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
//        );

        BinaryLogFileReader reader = new BinaryLogFileReader(binlogFile, eventDeserializer);
        try {
            for (Event event; (event = reader.readEvent()) != null; ) {

                switch (event.getHeader().getEventType()) {
                    case TABLE_MAP:
                        TableMapEventData eventData = event.getData();
                        if(databaseName.equals(eventData.getDatabase()) && tableName.equals(eventData.getTable())) {
                            tableId = eventData.getTableId();
                        }
                        break;
                    default:
                        break;
                }

                if(event.getHeader().getTimestamp() < beginTimestamp) {
                    continue;
                } else if(event.getHeader().getTimestamp() > endTimestamp) {
                    break;
                }

                switch (event.getHeader().getEventType()) {
                    case DELETE_ROWS:
                        this.handleDeleteRowsEvent(event.getData());
                        break;
                    case UPDATE_ROWS:
                        // todo
                        break;
                    case WRITE_ROWS:
                        // todo
                        break;
                    default:
                        break;
                }
            }

            logger.warn("/* {} */", count);
        } finally {
            reader.close();
        }
    }

    private void handleDeleteRowsEvent(DeleteRowsEventData deleteData) {
        if(deleteData.getTableId() != tableId) {
            return;
        }

        Iterator rows = deleteData.getRows().iterator();

        while(rows.hasNext()) {
            Object[] row = (Object[])rows.next();

            count++;
            StringBuilder sb = new StringBuilder(" insert into " + tableName + " values (");

            for(Object item : row) {
                if(item instanceof String) {
                    sb.append("'" + item + "'");
                } else if(item instanceof Date) {
                    String dateStr = this.adjustDateAndToString((Date) item);
                    sb.append("'" + dateStr + "'");
                }else {
                    sb.append(item);
                }
                sb.append(",");
            }
            sb.replace(sb.length() - 1, sb.length(), ");");

            logger.warn(sb.toString());
        }
    }

    private String adjustDateAndToString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -8); // 8 小时时差
        Date adjustDate = calendar.getTime();

        return simpleDateFormat.format(adjustDate);
    }
}
