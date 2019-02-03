using Microsoft.WindowsAPICodePack.Shell;
using System;

namespace file_taken_datetime
{
    class Program
    {
        static void Main(string[] args)
        {
            var file = @"D:\image\IMG_6789.JPG";

            ShellObject obj = ShellObject.FromParsingName(file);
            var takenDate = obj.Properties.System.ItemDate.Value;

            Console.WriteLine(takenDate);

            Console.ReadLine();
        }
    }
}
