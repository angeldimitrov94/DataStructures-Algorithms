using System;

namespace RecIntMult
{
    class Program
    {
        static void Main(string[] args)
        {
            int a = 0;
            int b = 0;
            int temp = 0;
            Console.WriteLine($"-----Recursive Integer Multiplication Demo-----");
            Console.Write(String.Format("{0,30}","Please enter first operand : "));
            if (int.TryParse(Console.ReadLine(), out temp)) a = temp;
            Console.Write(String.Format("{0,30}", "Please enter second operand : "));
            if (int.TryParse(Console.ReadLine(), out temp)) b = temp;
            long begin = DateTime.Now.Ticks;
            int result = Mult(a, b);
            long end = DateTime.Now.Ticks - begin;
            Console.WriteLine(String.Format("{0,30}", $"Result of {a}*{b} : ")+ result);
            Console.WriteLine(String.Format("{0,30}", $"Execution time : ")+$"{end / 100}µs");
            Console.ReadLine();
        }

		public static int Mult(int num1, int num2)
		{
            int n = num1.ToString().Length;

            if (n == 1) return num1 * num2;
			else
            {
                int a, b, c, d;
                a = b = c = d = 0;
                GetParts(num1, ref a, ref b);
                GetParts(num2, ref c, ref d);
                int ac = Mult(a, c);
                int ad = Mult(a, d);
                int bc = Mult(b, c);
                int bd = Mult(b, d);

                return ((int)Math.Pow(10,n) * ac) + ((int)Math.Pow(10, n/2) * (ad + bc)) + bd;
            }
        }

        private static void GetParts(int num, ref int firstPart, ref int secondPart)
        {
            string first = "";
            string second = "";

            for (int i = 0; i < num.ToString().Length; i++)
            {
                if (i < num.ToString().Length / 2)
                {
                    first += num.ToString()[i];
                }
                else
                {
                    second += num.ToString()[i];
                }
            }

            firstPart = int.Parse(first);
            secondPart = int.Parse(second);
        }
    }
}
