
/**
 * FeedForward is a program that uses artificial neural network
 * to analyze the content of images.
 * 
 * Ryerson FALL2016 CPS109 A_S_
 * V.1
 */
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
public class Feedforward
{
    public static void main(String args[])
    {
        for(int name = 0; name < 100; name++)
        {
            String imageName = ("feedforward/numbers/"+String.valueOf(name)+".png");//setting up the names of each image 0-99
            double[][] hiddenData = ReadFile("feedforward/hidden-weights.txt",300,785);//get hidden weights and bias
            double[][] outputData = ReadFile("feedforward/output-weights.txt",10,301);//get output weights and bias
            double[] imgData = ReadImage(imageName);//get imagedata
            //hidden layer
            double[] hidden = perceptron(hiddenData, imgData);//calculate the values with bias for hidden layer
            hidden = multitute(hidden);//then convert through multitute
            //output layer
            double[] output = perceptron(outputData, hidden);//calculate the values with bias for output layer
            output = multitute(output);//then convert through multitute
            int largest = 0;//for the largest possibility of the number
            for(int i =0; i<output.length; i++)
            {
                if(output[i]>output[largest])
                {
                    largest = i;//find the largest
                }
            }
            System.out.println("The network prediction is " + largest + ".");//print it out
        }

    }

    public static double[][] ReadFile(String fName, int rows, int vals)
    {
        double[][] nums = new double[rows][vals];//2D double array for storing file of certain size
        String temp = "";//read line by line
        try{
            BufferedReader inputFile = new BufferedReader(new FileReader(fName));
            for(int i = 0; i < rows; i++)
            {
                temp = inputFile.readLine();//read line through buffered reader
                Scanner scanner = new Scanner(temp);//scan the line for double convertion
                for(int j = 0; j < vals; j++)
                {
                    nums[i][j] = Double.parseDouble(scanner.next());//convert it
                }
            }
            inputFile.close();//close for computer goods
        }catch(IOException ex)
        {
            System.out.println("SOMEHTING WENT WRONG WITH FILE: " + fName);//indicate the error
        }
        return nums;//return the arraylist of string hold all the useful lines in the file
    }

    public static double[] perceptron(double[][] x, double[] w)
    {
        double[] re = new double[x.length];//double array for return
        if(x[0].length-1 == w.length)
        {
            for(int i = 0; i < x.length; i++)
            {
                for(int j = 0; j < w.length; j++)
                {
                    re[i] += x[i][j]*w[j];//matrix multiplication
                }
                re[i] += x[i][w.length];//add the bias
            }
        }
        else
        {
            System.out.println("THE SIZE OF INPUTS OF perceptron DOES NOT MATCH");//indicatinnnnnnnnng the errrrrrror
        }
        return re;//return lol
    }

    public static double[] multitute(double[] num)
    {
        double[] re = new double[num.length];//array same size for return
        //actually i can just make this a modifier right??? anyway
        for(int i = 0; i < num.length; i++)
        {
            re[i] = 1/(1+Math.pow(Math.E,(-num[i])));//calculate the multitute mathod
        }
        return re;
    }

    public static double[] ReadImage(String filename)
    {
        BufferedImage img = null;//coping the image reading lines from the assignment's pdf
        try{
            img = ImageIO.read(new File(filename));//program told me to use catch while compiling
        }catch(IOException e)
        {
            System.out.println("SOMETHING WENT WRONG WITH FILE: " + filename);//so i threw the exception
        }
        //get pixel data!!!
        double[] dummy = null;//same as given lines
        double[] x = img.getData().getPixels(0,0,img.getWidth(),img.getHeight(), dummy);//same as given lines
        //I am not sure if read RGB works, I deleted those lines. being a good kid.
        for(int i = 0; i< x.length; i++)
        {
            x[i] = x[i]/255.0;//making 0-255 to 0-1
        }
        return x;//return the xxxxxxxxx
    }

    public static String[][] ReadLabels(String name)
    {
        ArrayList<String> lines = new ArrayList();//storage
        String temp = "";//temp read line
        File labels = new File(name);
        try{
            FileReader fRead = new FileReader(labels);
            BufferedReader inputFile = new BufferedReader(fRead);
            while((temp = inputFile.readLine()) != null)
            { 
                lines.add(temp);//add
            }
            inputFile.close();//close reader
        }catch(IOException ex)
        {
            System.out.println("SOMEHTING WENT WRONG WITH FILE: " + labels);//indicate the error
        }
        //
        int count = 0;
        ArrayList<String[]> data = new ArrayList();
        for(int i = 0; i < lines.size(); i++)
        {
            Scanner scan = new Scanner(lines.get(i));
            while(scan.hasNext())
            {
                count++;
            }
            String[] adder = new String[count];//how many blocks are there in each line
            for(int j = 0; j < count; j++)
            {
                adder[j] = scan.next();
            }
            data.add(adder);
        }
        String[][] re = new String[lines.size()][count];
        for(int i = 0; i<re.length;i++)
        {
            for(int j = 0; j<re[0].length; j++)
            {
                re[i][j] = data.get(i)[j];
            }
        }
        return re;
    }
}
