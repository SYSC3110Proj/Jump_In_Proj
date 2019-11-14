package tree;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeepCopy {
	
	public static final Object createDeepCopy(Object obj) {
		try {
			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
			objectOutputStream.writeObject(obj);
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());
			ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
			return objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
