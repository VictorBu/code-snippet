using System;

namespace ModbusCrc16
{
    class Program
    {
        static void Main(string[] args)
        {
            // 0x01, 0x03, 0x00, 0x64, 0x00, 0x65, 0xC4, 0x3E
            byte[] data = new byte[] { 0x01, 0x03, 0x00, 0x64, 0x00, 0x65 };

            var crc = GetModbusCrc16(data);

            Console.WriteLine("C43E" == ToHexString(crc));

            Console.ReadLine();
        }

        public static byte[] GetModbusCrc16(byte[] bytes)
        {
            byte crcRegister_H = 0xFF, crcRegister_L = 0xFF;// 预置一个值为 0xFFFF 的 16 位寄存器

            byte polynomialCode_H = 0xA0, polynomialCode_L = 0x01;// 多项式码 0xA001

            for (int i = 0; i < bytes.Length; i++)
            {
                crcRegister_L = (byte)(crcRegister_L ^ bytes[i]);

                for (int j = 0; j < 8; j++)
                {
                    byte tempCRC_H = crcRegister_H;
                    byte tempCRC_L = crcRegister_L;

                    crcRegister_H = (byte)(crcRegister_H >> 1);
                    crcRegister_L = (byte)(crcRegister_L >> 1);
                    // 高位右移前最后 1 位应该是低位右移后的第 1 位：如果高位最后一位为 1 则低位右移后前面补 1
                    if ((tempCRC_H & 0x01) == 0x01)
                    {
                        crcRegister_L = (byte)(crcRegister_L | 0x80);
                    }

                    if ((tempCRC_L & 0x01) == 0x01)
                    {
                        crcRegister_H = (byte)(crcRegister_H ^ polynomialCode_H);
                        crcRegister_L = (byte)(crcRegister_L ^ polynomialCode_L);
                    }
                }
            }

            return new byte[] { crcRegister_L, crcRegister_H };
        }

        private static string ToHexString(byte[] bytes)
        {
            if (bytes != null)
            {
                System.Text.StringBuilder hexString = new System.Text.StringBuilder();
                for (int i = 0; i < bytes.Length; i++)
                {
                    hexString.Append(bytes[i].ToString("X2"));
                }
                return hexString.ToString();
            }

            return string.Empty;

        }

    }
}
