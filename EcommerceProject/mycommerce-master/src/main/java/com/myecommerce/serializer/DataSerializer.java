package com.myecommerce.serializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.myecommerce.dto.ProductDTO;
import com.myecommerce.exception.BusinessException;

public class DataSerializer {
	
	public static void serializeProduct(ProductDTO productDTO)
	{
		FileOutputStream fos=null;
		
		ObjectOutputStream oos=null;
		File file=new File(productDTO.getProductId()+".ser");
		try {
			 fos=new FileOutputStream(file);
             oos=new ObjectOutputStream(fos);
             oos.writeObject(productDTO);
		} catch (IOException e)
				  {
			e.printStackTrace();
		}
		finally {
			try {
				oos.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			  
		}
		
	}

	public static ProductDTO deserializeProduct(Long productId)
	{
		File file=new File(productId+".ser");
		FileInputStream fis=null;
		ObjectInputStream ois=null;
		try {
			fis=new FileInputStream(file);
		ois=new ObjectInputStream(fis);
		return (ProductDTO)ois.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		return null;
	}
}
