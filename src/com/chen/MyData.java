package com.chen;

import java.util.ArrayList;

class MyData
{ 
	private String name;
	private Integer type;//0��ʾ���ᣬ��0��ʾ����
	private Integer x;//������
	private Integer y;//������
	private ArrayList<Integer> path;//��ÿ������룬100��ʾ����û��·��ֱ������

	public MyData()
	{
		this.name=null;
		this.x=this.y=0;
		this.path=new ArrayList<Integer>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public Integer getType(){
		return type;
	}
	public void setType(int type) {
		this.type=type;
	}
	public Integer getX() {
		return x;
	}
	public void setX(int x) {
		this.x=x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(int y) {
		this.y=y;
	}
	public ArrayList<Integer> getPath(){
		return path;
	}
	public void setPath(ArrayList<Integer> path) {
		this.path=path;
	}
	
}
