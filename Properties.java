package org.rrd4j.jl;

import java.util.Vector;

public class Properties {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Prop pro = new Prop(); 
//		pro.createBaseGraph("C:/rrd/prop.properties");
//		pro.createBaseGraph("C:/rrd/prop1.properties");
		System.out.println(pro.readBaseGraphes());
//		pro.createGraphP(ve, "dbName", "C:/rrd/pathGraph", "1", "2", "ja");
//		Vector<String> ve = new Vector<String>();
//		ve.add("C:/rrd/pfad.rrd");
//		ve.add("C:/rrd/pfad2.rrd");
	}

}
