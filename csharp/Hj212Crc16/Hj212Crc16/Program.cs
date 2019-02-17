using System;

namespace Hj212Crc16
{
    class Program
    {
        static void Main(string[] args)
        {
            // QN=20160801085857223;ST=32;CN=1062;PW=100000;MN=010000A8900016F000169DC0;Flag=5;CP=&&RtdInterval=30&&1C80
            var str = "QN=20160801085857223;ST=32;CN=1062;PW=100000;MN=010000A8900016F000169DC0;Flag=5;CP=&&RtdInterval=30&&";

            var crc = HJ2GetHj212Crc1612CRC(str);

            Console.WriteLine("1C80" == crc);

            Console.ReadLine();
        }

        public static string HJ2GetHj212Crc1612CRC(string content)
        {
            byte[] bytes = System.Text.Encoding.ASCII.GetBytes(content);

            return GetHj212Crc16(bytes);
        }

        public static string GetHj212Crc16(byte[] bytes)
        {
            int crcRegister = 0xFFFF;
            for (int i = 0; i < bytes.Length; i++)
            {
                crcRegister = (crcRegister >> 8) ^ bytes[i];
                for (int j = 0; j < 8; j++)
                {
                    int check = crcRegister & 0x0001;
                    crcRegister >>= 1;
                    if (check == 0x0001)
                    {
                        crcRegister ^= 0xA001;
                    }
                }
            }

            string result = string.Format("{0:X}", crcRegister);//转十六进制
            for (int i = result.Length; i < 4; i++)//补足 4 位
            {
                result = "0" + result;
            }

            return result;
        }

    }
}
