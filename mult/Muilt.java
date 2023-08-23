import java.util.Scanner;
class Muilt
{
    public static void main(String[] arg)
    {
        Scanner sc=new Scanner(System.in);
        int counter=10;
        while(counter>0)
        {
            System.out.println("enter the number one");
            int number1=sc.nextInt();
            System.out.println("enter the number two");
            int number2=sc.nextInt();
            System.out.println(number1+"*"+number2+"="+(number1*number2));
            counter--;

        }
        sc.close();
    }
}