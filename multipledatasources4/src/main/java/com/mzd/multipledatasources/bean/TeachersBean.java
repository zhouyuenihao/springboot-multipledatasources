package com.mzd.multipledatasources.bean;

import java.io.Serializable;

public class TeachersBean implements Serializable {

	private String id;
	private String teachername;
	private String classid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

}
