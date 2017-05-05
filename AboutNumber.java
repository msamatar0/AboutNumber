import java.io.StringWriter;
import java.util.TreeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author msamatar0
 */
public class AboutNumber{
    private AboutNumber(){}
    
    public static boolean isPrime(int number){
        if (number < 2)
            return false;
        if (number == 2)
            return true;
        if (number % 2 == 0)
            return false;
        for (int i = 3; i * i <= number; i += 2)
            if (number % i == 0)
                return false;
        return true;
    }

    public static boolean isEven(int number){
        return number % 2 == 0;
    }

    public static boolean isOdd(int number){
        return !isEven(number);
    }

    public static boolean isPalindrome(int number){
        String numStr = Integer.toString(number);
        String revNum = new StringBuilder(numStr).reverse().toString();
        return numStr.equals(revNum);
    }

    public static boolean isPalindromicPrime(int number){
        return isPalindrome(number) && isPrime(number);
    }

    public static boolean isFibonacci(int number){
        double r1 = Math.sqrt(number * number) + 4,
               r2 = Math.sqrt(number * number) - 4;
        return r1 == Math.floor(r1)
               && r2 == Math.floor(r2);
    }

    public static boolean isPerfectSquare(int number){
        double root = Math.sqrt(number);
        return root == Math.floor(root);
    }

    public static TreeSet primeFactors(int number){
        TreeSet<Integer> factors = new TreeSet<>();
        for(int i = 1; i < number; ++i)
            if(number % i == 0 && isPrime(i))
                factors.add(i);
        return factors;
    }
    
    /**
     * @author tdedonno
     * @param number
     * @return XML output
     * @throws Exception
     */
    public String XMLOutput(int number) throws Exception{
        DocumentBuilderFactory  factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = factory.newDocumentBuilder();
        Document doc = b.newDocument();

        Element root = doc.createElement("AboutNumbers");

        //for computer generated code you normally don't add extra white spaces, for testing we need readability
        //root.appendChild(doc.createTextNode("\n"));
        //Create XML file and use right Click Format
        Element firstChild  = doc.createElement("AboutNumber");
        Element child = doc.createElement("Number");
        Text text = doc.createTextNode(Integer.toString(number));
        child.appendChild(text);
        firstChild.appendChild(child);

        String[] titles =  { "Prime", "Even", "Odd", "Palindrome", "PalindromicPrime", "Fibonacci", "PerfectSquare"  };
        // Note java SE7 supports String case statements, but se7 is not everywhere so even though I should use it I'm not

        for(int i = 0; i < titles.length ; ++i){
            //root.appendChild(doc.createTextNode("\n"));
            child = doc.createElement("NumberInfo");
            try{
                boolean result = false;
                switch(i){
                    case 0:
                        result = isPrime(number);
                        break;
                    case 1:
                        result = isEven(number);
                        break;
                    case 2:
                        result = isOdd(number);
                        break;
                    case 3:
                        result = isPalindrome(number);
                        break;
                    case 4:
                        result = isPalindromicPrime(number);
                        break;
                    case 5:
                        result = isFibonacci(number);
                        break;
                    case 6:
                        result = isPerfectSquare(number);
                        break;
                    default:
                        throw new UnsupportedOperationException("Unknown isNames Method");
                    }
                text = doc.createTextNode(Boolean.toString(result));
            }
            catch(UnsupportedOperationException e){
                text = doc.createTextNode(e.getMessage());
            }

            child.setAttribute("title",  titles[i]);
            child.setAttribute("description", "not yet implemented");
            child.appendChild(text);
            firstChild.appendChild(child);

        } //end for(int = 0;

        /* You need to write the code to output the primeFactors, make sure you meet the aboutNumber dtd or xsd
         * until you have written all isMethods, the output xml will not pass validation, 
         * XSD is much more verbose than DTD, XSD exists on lab page and is more verbose than DTD. 

         * assigment page has link to DTD
         */
        root.appendChild(firstChild);
        doc.appendChild(root);

        // doc.getDomConfig().setParameter(null, out)
        TransformerFactory transformerFactory =  TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter out = new StringWriter(); //note out could be System.out
        transformer.transform(new DOMSource(doc), new StreamResult(out));
        return out.toString();
    }
}
