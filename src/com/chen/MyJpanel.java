package com.chen;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;



class MyJpanel extends JPanel{
	
	public static final int INFINITY = 9999;
	private static final double RESOLUTION = 1000;
	private List<MyData> data;
	private int numOfView = 0;//记录景点数
	private int numOfDor = 0;//记录宿舍数
	private Graphics jg; 	
	private String filePath;
	

	public MyJpanel(String pathname) {
		ReadFile(pathname);//加载文件
	}
	
	//设置画笔
	public void setG() {
		this.jg = this.getGraphics();
	}
	
	public void setPointX(int point,int x) {
		this.data.get(point).setX(x);;
	}
	
	public void setPointY(int point,int y) {
		this.data.get(point).setY(y);;
	}
	
	public int setPointName(int point,String name) {
		if(this.data.get(point).getName()!=name) {
			this.data.get(point).setName(name);
			return 1;
		}
		return 0;
	}
	
	public int setPointType(int point,int type) {
		if(this.data.get(point).getType()!=type) {
			MyData temp=new MyData();
			temp=delPoint(point);
			temp.getPath().remove(point);
			temp.setType(type);
			this.addPoint(temp);
			return 1;
		}
		return 0;
	}
	
	public MyData delPoint(int point) {
		if(point<0||this.data.size()==0) {
			return null;
		}
		if(this.data.get(point).getType()==0) {
			this.numOfDor--;
		}
		if(this.data.get(point).getType()==1) {
			this.numOfView--;
		}
		MyData temp = new MyData();
		temp=this.data.remove(point);
		for(int i=0;i<this.data.size();i++) {
			this.data.get(i).getPath().remove(point);
		}
		return temp;
	}
	
	public void addPoint(MyData data) {
		    data.setType(0);//设置初始类型为0
			for(int i=0;i<this.data.size();i++) {
				this.data.get(i).getPath().add(this.numOfView, data.getPath().get(i));
			}
			data.getPath().add(this.numOfView, 0);
			this.data.add(this.numOfView, data);
			if(data.getType()==1) {
				this.numOfView++;
			}
			if(data.getType()==0) {
				this.numOfDor++;
			}
		}

	
	public void setLength(int startPoint,int endPoint,int length) {
		if(this.data.get(startPoint).getPath().get(endPoint)!=length) {
			this.data.get(startPoint).getPath().set(endPoint, length);
			this.data.get(endPoint).getPath().set(startPoint, length);
			this.repaint();
		}
	}
	
	public int getNumofPoint() {
		return this.data.size();
	}
	
	//返回景点和宿舍的名字
 	public String[] getAllPointName(){
 		String[] a =new String[this.data.size()];
 		for(int i=0;i<this.data.size();i++) {
 			a[i]=(this.data.get(i).getName());
 		}
		return a;	
 	}
	
	public int mouseClick(MouseEvent e) {
		double sum=MyJpanel.RESOLUTION+1;
		int point=Integer.MAX_VALUE;
		for(int i=0;i<this.data.size();i++) {
			double temp=(this.data.get(i).getX()-e.getX())*(this.data.get(i).getX()-e.getX())+(this.data.get(i).getY()-e.getY())*(this.data.get(i).getY()-e.getY());
			if(temp<sum) {
				sum=temp;
				point=i;
			}
		}
		return point;
	}
	
	public  int getPointX(int point) {
		if(point<0||this.data.size()==0) {
			return 0;
		}
		return this.data.get(point).getX();
	}
	
	public int getPointY(int point) {
		if(point<0||this.data.size()==0) {
			return 0;
		}
		return this.data.get(point).getY();
	}
	
	public int getPointType(int point) {
		if(point<0||this.data.size()==0) {
			return 0;
		}
		return this.data.get(point).getType();
	}
	
	//返回两点之间的距离
	public int getLength(int point1,int point2) {
		if(point1<0||point2<0||this.data.size()==0) {
			return 0;
		}
		return this.data.get(point1).getPath().get(point2);
	}
 	
	
	public void writeFile() {
		File writename = new File(this.filePath); // 相对路径，如果没有则要建立一个新的output。txt文件
		try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			for(int i=0;i<this.data.size();i++) {
				out.write(this.data.get(i).getName());
				out.write("      ");
				out.write(this.data.get(i).getType().toString());
				out.write("      ");
				out.write(String.valueOf(this.data.get(i).getX()));
				out.write("      ");
				out.write(String.valueOf(this.data.get(i).getY()));
				out.write("      ");
				for(int j=0;j<this.data.size();j++) {
					out.write(this.data.get(i).getPath().get(j).toString());
					out.write("      ");
				}
				out.newLine();
			}
			out.flush(); // 把缓存区内容压入文件
			out.close(); //
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件
		
	}
	
	//初始化拓扑图
	 private void showInit(Graphics jg) {
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}    //???    

			jg.setFont(new Font("宋体", 0, 20));
			
			for(int i = 0;i<this.data.size();i++) {
				jg.setColor(Color.black );//路径和权值设置为绿色
				for(int j = i+1;j<this.data.size();j++) {
					if(this.data.get(i).getPath().get(j)<MyJpanel.INFINITY)
					{
						//画路径
						jg.drawLine(this.data.get(i).getX(), this.data.get(i).getY(), this.data.get(j).getX(), this.data.get(j).getY());	
						//权值
						jg.drawString(this.data.get(i).getPath().get(j).toString(),( this.data.get(i).getX()+this.data.get(j).getX())/2, (this.data.get(i).getY()+this.data.get(j).getY())/2);	
					}
				}
				if(this.data.get(i).getType()==1)
				{
					jg.setColor(Color.black );//景点名字字体为黑色
					jg.drawString(this.data.get(i).getName(), this.data.get(i).getX(), this.data.get(i).getY());
				}
				else if(this.data.get(i).getType()==0)
				{
					jg.setColor(Color.blue );//宿舍名字字体为蓝色
					jg.drawString(this.data.get(i).getName(), this.data.get(i).getX(), this.data.get(i).getY());
				}
				else if(this.data.get(i).getType()==2)
				{
					jg.setColor(Color.green );//教学楼名字字体为绿色
					jg.drawString(this.data.get(i).getName(), this.data.get(i).getX(), this.data.get(i).getY());
				}
			}
		}
	 	
		
	
 	//读取文件
	private void ReadFile(String pathname) {
		this.filePath=pathname;
		this.data = new ArrayList<MyData>();
		try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
			/* 读入TXT文件 */
			File file = new File(pathname); // 要读取以上路径的input.txt文件
			InputStreamReader reader = new InputStreamReader(
					new FileInputStream(file)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = new String();
			line = br.readLine();
			while (line != null) {
				
				String[] splited = line.split("\\s+");//以空格 分割
				
				MyData tempdata = new MyData();//创建临时对象保存改行数据
				
				tempdata.setName(splited[0]);
				tempdata.setType(Integer.parseInt(splited[1]));
				if(tempdata.getType()==0) {
					this.numOfDor++;
				}
				if(tempdata.getType()==1) {
					this.numOfView++;
				}
				tempdata.setX(Integer.parseInt(splited[2]));
				tempdata.setY(Integer.parseInt(splited[3]));
				for(int i=4;i<splited.length;i++)
				{
					tempdata.getPath().add( Integer.parseInt(splited[i]) );
				}
				this.data.add(tempdata);
				line = br.readLine(); // 读入下一行数据
			}
			br.close();
			reader.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
 	

	
	@Override
    public void paint(Graphics g) {
		super.paint(g);	
		showInit(g);
	}
}
