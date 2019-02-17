package com.chen;

import java.util.ArrayList;

class MyData
{ 
	private String name;
	private Integer type;//0表示宿舍，非0表示景点
	private Integer x;//横坐标
	private Integer y;//纵坐标
	private ArrayList<Integer> path;//到每个点距离，100表示两点没有路径直接相连

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
