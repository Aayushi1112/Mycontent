//
//public class TestThrow1 {   
//    //function to check if person is eligible to vote or not 
//	
//	 public static void validating(int age) { 
//		 try {
//	        if(age<18) {  
//	             
//	        	 TestThrow2.validate(age);  
//	        }  
//	        else {  
//	            System.out.println("Person is eligible to vote!!");  
//	        }  }
//		 catch(Exception e) {
//			  throw new ArithmeticException("Person is not eligible to vote");  
//		 }
//	    }
//     
//    //main method  
//    public static void main(String args[]){  
//    	try {
//        //calling the function  
//        validating(13);  
//        System.out.println("rest of the code...");    
//   }
//    catch(Exception e) {
//    	System.out.println("exc");
//    }}
//}    