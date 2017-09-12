package org.gj.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class CollObj {
	@Test
	public void objOfSet() {
		Map<String,Set<String>> m = new HashMap<>();
		Set<String> set = new HashSet<>();
		set.add("aaa");//first element in list
        m.put("1",set);
        //predefined original value of key "1"
        System.out.println("key = 1, "+"value = "+m.get("1").toString());
        //update value of key "1"
        set.add("bbb");
        System.out.println("key = 1, "+"value = "+m.get("1").toString());
	}
	public static void main(String[] args) {
        // TODO Auto-generated method stub
 
        Map<String,List<String>> m = new HashMap<String, List<String>>();
        List<String> l = new ArrayList<String>();
        l.add("aaa");//first element in list
        m.put("1",l);
        //predefined original value of key "1"
        System.out.println("key = 1, "+"value = "+m.get("1").toString());
        //update value of key "1"
        l.add("bbb");
        System.out.println("key = 1, "+"value = "+m.get("1").toString());
         
    }
}
