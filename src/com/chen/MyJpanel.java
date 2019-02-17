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
	private int numOfView = 0;//��¼������
	private int numOfDor = 0;//��¼������
	private Graphics jg; 	
	private String filePath;
	

	public MyJpanel(String pathname) {
		ReadFile(pathname);//�����ļ�
	}
	
	//���û���
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
		    data.setType(0);//���ó�ʼ����Ϊ0
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
	
	//���ؾ�������������
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
	
	//��������֮��ľ���
	public int getLength(int point1,int point2) {
		if(point1<0||point2<0||this.data.size()==0) {
			return 0;
		}
		return this.data.get(point1).getPath().get(point2);
	}
 	
	
	public void writeFile() {
		File writename = new File(this.filePath); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
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
			out.flush(); // �ѻ���������ѹ���ļ�
			out.close(); //
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // �������ļ�
		
	}
	
	//��ʼ������ͼ
	 private void showInit(Graphics jg) {
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}    //???    

			jg.setFont(new Font("����", 0, 20));
			
			for(int i = 0;i<this.data.size();i++) {
				jg.setColor(Color.black );//·����Ȩֵ����Ϊ��ɫ
				for(int j = i+1;j<this.data.size();j++) {
					if(this.data.get(i).getPath().get(j)<MyJpanel.INFINITY)
					{
						//��·��
						jg.drawLine(this.data.get(i).getX(), this.data.get(i).getY(), this.data.get(j).getX(), this.data.get(j).getY());	
						//Ȩֵ
						jg.drawString(this.data.get(i).getPath().get(j).toString(),( this.data.get(i).getX()+this.data.get(j).getX())/2, (this.data.get(i).getY()+this.data.get(j).getY())/2);	
					}
				}
				if(this.data.get(i).getType()==1)
				{
					jg.setColor(Color.black );//������������Ϊ��ɫ
					jg.drawString(this.data.get(i).getName(), this.data.get(i).getX(), this.data.get(i).getY());
				}
				else if(this.data.get(i).getType()==0)
				{
					jg.setColor(Color.blue );//������������Ϊ��ɫ
					jg.drawString(this.data.get(i).getName(), this.data.get(i).getX(), this.data.get(i).getY());
				}
				else if(this.data.get(i).getType()==2)
				{
					jg.setColor(Color.green );//��ѧ¥��������Ϊ��ɫ
					jg.drawString(this.data.get(i).getName(), this.data.get(i).getX(), this.data.get(i).getY());
				}
			}
		}
	 	
		
	
 	//��ȡ�ļ�
	private void ReadFile(String pathname) {
		this.filePath=pathname;
		this.data = new ArrayList<MyData>();
		try { // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw
			/* ����TXT�ļ� */
			File file = new File(pathname); // Ҫ��ȡ����·����input.txt�ļ�
			InputStreamReader reader = new InputStreamReader(
					new FileInputStream(file)); // ����һ������������reader
			BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������

			String line = new String();
			line = br.readLine();
			while (line != null) {
				
				String[] splited = line.split("\\s+");//�Կո� �ָ�
				
				MyData tempdata = new MyData();//������ʱ���󱣴��������
				
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
				line = br.readLine(); // ������һ������
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
