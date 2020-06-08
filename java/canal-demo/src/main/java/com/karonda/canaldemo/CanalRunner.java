package com.karonda.canaldemo;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CanalRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CanalRunner.class);

    @Override
    public void run(String... args) throws Exception {

        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress("192.168.137.101", 11111)
                , "example"
                , "canal", "canal");
        connector.connect();
        connector.subscribe(".*\\..*");
        connector.rollback();

        while (true) {
            Message message = connector.getWithoutAck(100); // 获取指定数量的数据
            long batchId = message.getId();
            if (batchId == -1 || message.getEntries().isEmpty()) {
                Thread.sleep(1000);
                continue;
            }

            try {
                printEntries(message.getEntries());
                connector.ack(batchId);// 提交确认，消费成功，通知server删除数据
            } catch (Exception e) {
//                connector.rollback(batchId);// 处理失败, 回滚数据，后续重新获取数据
            }

        }

    }

    private void printEntries(List<CanalEntry.Entry> entries) throws Exception {
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() != CanalEntry.EntryType.ROWDATA) {
                continue;
            }

            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());

            CanalEntry.EventType eventType = rowChange.getEventType();

            LOGGER.info("数据库名: {}, 数据表名: {}, 事件: {}"
                    , entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType);

            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                switch (rowChange.getEventType()) {
                    case INSERT:
                        printColumns(rowData.getAfterColumnsList());
                        break;
                    case UPDATE:
                        LOGGER.info("更新前数据：");
                        printColumns(rowData.getBeforeColumnsList());
                        LOGGER.info("更新后数据：");
                        printColumns(rowData.getAfterColumnsList());
                        break;
                    case DELETE:
                        printColumns(rowData.getBeforeColumnsList());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void printColumns(List<CanalEntry.Column> columns) {
        Map<String, Object> map = new HashMap<>();
        for(CanalEntry.Column column : columns) {
            map.put(column.getName(), column.getValue());
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            LOGGER.info(objectMapper.writeValueAsString(map));
        } catch (JsonProcessingException e) {
        }
    }
}
