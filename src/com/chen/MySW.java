package com.chen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;





public class MySW {
	
	private int xx;
	private int yy;
	
	public JFrame Jf;
	public JLabel lineStartPoint;
	public JLabel lineEndPoint;
	public JLabel length;
	public JComboBox<String> lineStartPointName;
	public JComboBox<String> lineEndPointName;
	public JFormattedTextField lineLength;
	public JButton lineLengthSave;
	public JButton lineDel;
	public MyJpanel MJp;
	public JPanel Jp;
	
	
	//�洢�ڵ���Ϣ
	public JPanel pointJP;
	public JLabel pointInfoName;
	public JLabel pointInfoX;
	public JLabel pointInfoY;
	public JTextField changPoingName;
	public JComboBox<String> pointName;
	public JComboBox<String> poitType;
	public JFormattedTextField pointX;
	public JFormattedTextField pointY;
	public JButton pointSave;
	public JButton pointDel;
	public JButton pointAdd;
	public int DragPoint=9999;
	
	public JPanel JpNorth;
	public JPanel JpSorth;
	public JButton Save;

	public MySW(String pathname){
		this.MJp = new MyJpanel(pathname);
		
		//�ڵ�
		this.pointInfoName = new JLabel("����");
		Font h1=new Font("����", Font.CENTER_BASELINE, 20);
		pointInfoName.setFont(h1);
		this.pointInfoName.setPreferredSize(new Dimension(50,50));
		
		this.pointName=new JComboBox<String>(MJp.getAllPointName());
		this.pointName.setPreferredSize(new Dimension(100,50));
		this.pointName.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					int i=pointName.getSelectedIndex();
					changPoingName.setText(pointName.getSelectedItem().toString());
					poitType.setSelectedIndex(MJp.getPointType(i));
					pointX.setValue(MJp.getPointX(i));
					pointY.setValue(MJp.getPointY(i));
				}
				
			}
		});
		
		
		this.changPoingName=new JTextField(this.pointName.getSelectedItem().toString());
		this.changPoingName.setPreferredSize(new Dimension(100,50));
		
		String [] a= {"����","����","����"};
 		this.poitType=new JComboBox<String>(a);
 		this.poitType.setPreferredSize(new Dimension(100,50));
 		this.poitType.setSelectedIndex(MJp.getPointType(this.pointName.getSelectedIndex()));
 		
 		this.pointInfoX = new JLabel("X:");
		Font h2=new Font("����", Font.CENTER_BASELINE, 20);
		pointInfoX.setFont(h2);
		this.pointInfoX.setPreferredSize(new Dimension(23,50));
 		this.pointX=new JFormattedTextField(NumberFormat.getIntegerInstance());
 		this.pointX.setPreferredSize(new Dimension(100,50));
 		this.pointInfoY = new JLabel("Y:");
		Font h3=new Font("����", Font.CENTER_BASELINE, 20);
		pointInfoY.setFont(h3);
		this.pointInfoY.setPreferredSize(new Dimension(23,50));
 		this.pointX.setValue(MJp.getPointX(this.pointName.getSelectedIndex()));
 		this.pointY=new JFormattedTextField(NumberFormat.getIntegerInstance());
 		this.pointY.setPreferredSize(new Dimension(100,50));
 		this.pointY.setValue(MJp.getPointY(this.pointName.getSelectedIndex()));
 		
 		this.pointSave= new JButton("�����޸�");
 		this.pointSave.setPreferredSize(new Dimension(100,50));
 		this.pointSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name=changPoingName.getText();
				int isNameChange=MJp.setPointName(pointName.getSelectedIndex(), name);
				int x =Integer.parseInt(pointX.getValue().toString());
				MJp.setPointX(pointName.getSelectedIndex(), x);
				int y =Integer.parseInt(pointY.getValue().toString());
				MJp.setPointY(pointName.getSelectedIndex(), y);
				int type=poitType.getSelectedIndex();
				int isTypeChange=MJp.setPointType(pointName.getSelectedIndex(), type);
				if(isNameChange==1||isTypeChange==1) {
					String[] a=MJp.getAllPointName();
					pointName.removeAllItems();
					lineStartPointName.removeAllItems();
					lineEndPointName.removeAllItems();
					for(int i=0;i<a.length;i++) {
						pointName.addItem(a[i]);
						lineStartPointName.addItem(a[i]);
						lineEndPointName.addItem(a[i]);
					}				
				}
				MJp.repaint();
			}			
		}); 		
 		this.pointDel=new JButton("ɾ���ڵ�");
 		this.pointDel.setPreferredSize(new Dimension(100,50));
 		this.pointDel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int p=pointName.getSelectedIndex();
				MJp.delPoint(p);
				String[] a=MJp.getAllPointName();
				pointName.removeAllItems();
				lineStartPointName.removeAllItems();
				lineEndPointName.removeAllItems();
				for(int i=0;i<a.length;i++) {
					pointName.addItem(a[i]);
					lineStartPointName.addItem(a[i]);
					lineEndPointName.addItem(a[i]);
				}
				MJp.repaint();
			}
 			
 		});
 		
 		this.pointAdd=new JButton("���ӽڵ�");
 		this.pointAdd.setPreferredSize(new Dimension(100,50));
 		this.pointAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				MyData temp =new MyData();
				temp.setName("�½ڵ�");
				temp.setX(xx);
				temp.setY(yy);
				for(int i=0;i<MJp.getNumofPoint();i++) {
					temp.getPath().add(MyJpanel.INFINITY);
				}
				MJp.addPoint(temp);
				String[] a=MJp.getAllPointName();
				pointName.removeAllItems();
				lineStartPointName.removeAllItems();
				lineEndPointName.removeAllItems();
				for(int i=0;i<a.length;i++) {
					pointName.addItem(a[i]);
					lineStartPointName.addItem(a[i]);
					lineEndPointName.addItem(a[i]);
				}
				pointName.setSelectedItem("�����ڵ�");
				lineStartPointName.setSelectedItem("�����ڵ�");
				MJp.repaint();
			}
 			
 		});
 		
		this.pointJP=new JPanel();
		this.pointJP.setPreferredSize(new Dimension(1000,55));
		this.pointJP.setBackground(Color.YELLOW);
		this.pointJP.add(this.pointName);
		this.pointJP.add(this.pointInfoName);
		this.pointJP.add(this.changPoingName);
		this.pointJP.add(this.poitType);
		this.pointJP.add(this.pointInfoX);
		this.pointJP.add(this.pointX);
		this.pointJP.add(this.pointInfoY);
		this.pointJP.add(this.pointY);
		this.pointJP.add(this.pointSave);
		this.pointJP.add(this.pointDel);
		this.pointJP.add(this.pointAdd);
		
		//·��
		this.lineStartPoint = new JLabel("��	��");
		Font h4=new Font("����", Font.CENTER_BASELINE, 20);
		lineStartPoint.setFont(h4);
		lineStartPoint.setPreferredSize(new Dimension(50,50));
		//��ʾ�������
		this.lineStartPointName=new JComboBox<String>(MJp.getAllPointName());
		lineStartPointName.setPreferredSize(new Dimension(100,50));
		this.lineStartPointName.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					 lineLength.setValue(MJp.getLength(lineStartPointName.getSelectedIndex(), lineEndPointName.getSelectedIndex()));
				}
				
			}
		});

		this.lineEndPoint = new JLabel("�յ�");
		Font h5=new Font("����", Font.CENTER_BASELINE, 20);
		lineEndPoint.setFont(h5);
		lineEndPoint.setPreferredSize(new Dimension(50,50));
		//��ʾ�յ�����
		this.lineEndPointName=new JComboBox<String>(MJp.getAllPointName());
		lineEndPointName.setPreferredSize(new Dimension(100,50));
		this.lineEndPointName.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					 lineLength.setValue(MJp.getLength(lineStartPointName.getSelectedIndex(), lineEndPointName.getSelectedIndex()));
				}//ʹ�õ��ʱ���ظ�����һ��ֵ		
			}
		});

		this.length = new JLabel("��	��");
		Font h6=new Font("����", Font.CENTER_BASELINE, 20);
		length.setFont(h6);
		this.length .setPreferredSize(new Dimension(50,50));
		//��ʾ�յ����
		this.lineLength=new JFormattedTextField(NumberFormat.getIntegerInstance());
		this.lineLength.setPreferredSize(new Dimension(100,50));
		this.lineLength.setValue(MJp.getLength(this.lineStartPointName.getSelectedIndex(), this.lineEndPointName.getSelectedIndex()));
		
		this.lineLengthSave=new JButton("�������");
		this.lineLengthSave.setPreferredSize(new Dimension(100,50));
		this.lineLengthSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int i =Integer.parseInt(lineLength.getValue().toString());
				MJp.setLength(lineStartPointName.getSelectedIndex(), lineEndPointName.getSelectedIndex(), i);
			}
			
		});
		
		
		//���¸ð�ť��ɾ���ߣ���������֮��ĳ�������Ϊ9999
		this.lineDel=new JButton("ɾ����");
		this.lineDel.setPreferredSize(new Dimension(100,50));
		this.lineDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MJp.setLength(lineStartPointName.getSelectedIndex(), lineEndPointName.getSelectedIndex(), Integer.MAX_VALUE);
				//����������е�ֵΪInteger.MAX_VALUE
				lineLength.setValue(MJp.getLength(lineStartPointName.getSelectedIndex(), lineEndPointName.getSelectedIndex()));
			}			
		});	
		
		this.Save=new JButton("��������ͼ");
		this.Save.setPreferredSize(new Dimension(100,50));
		this.Save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MJp.writeFile();			
			}
			
		});
		
		this.Jp = new JPanel();
		this.Jp.setPreferredSize(new Dimension(1000, 55));
		this.Jp.setBackground(Color.YELLOW);
		this.Jp.add(lineStartPoint);
		this.Jp.add(lineStartPointName);
		this.Jp.add(lineEndPoint);
		this.Jp.add(lineEndPointName);
		this.Jp.add(length);
		this.Jp.add(lineLength);
		this.Jp.add(this.lineLengthSave);
		this.Jp.add(this.lineDel);
		this.Jp.add(this.Save);

		
		MJp.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				 int point=MJp.mouseClick(e);
				 if(point<MJp.getNumofPoint()) {
					 pointName.setSelectedIndex(point);
					 lineStartPointName.setSelectedIndex(lineEndPointName.getSelectedIndex());
					 lineEndPointName.setSelectedIndex(point);
				 }
			    }
			 public void mousePressed(MouseEvent e) {
				 System.out.println("X:"+e.getX()+"    Y:"+e.getY());
				 DragPoint=MJp.mouseClick(e);
			 }
			 public void mouseReleased(MouseEvent e) {
				 xx=e.getX();
				 yy=e.getY();
				 System.out.println("X:"+e.getX()+"    Y:"+e.getY());
				 DragPoint=Integer.MAX_VALUE;
			 }	
		});
		MJp.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if(DragPoint<MJp.getNumofPoint()) {
					pointName.setSelectedIndex(DragPoint);
					pointX.setValue(e.getX());
					pointY.setValue(e.getY());
					MJp.setPointX(DragPoint, e.getX());
					MJp.setPointY(DragPoint, e.getY());
					MJp.repaint();
				}
			}
		});
		
		
		this.JpNorth=new JPanel();
		this.JpNorth.setPreferredSize(new Dimension(1000, 120));
		this.JpNorth.setBackground(Color.YELLOW);
		this.JpNorth.add(this.pointJP,BorderLayout.NORTH);
		this.JpNorth.add(this.Jp,BorderLayout.NORTH);
		
		

		
		this.Jf = new JFrame("�޸ĵ�ͼ���������Աʹ�ã�");	
		Container p = Jf.getContentPane();
		p.setBackground(Color.white);
		Jf.setBounds(500, 40, 1000,850);
		Jf.setLayout(new BorderLayout());   
		Jf.setResizable(false);//���ɵ���С
		Jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Jf.add(this.JpNorth,BorderLayout.NORTH);
		Jf.add(MJp,BorderLayout.CENTER);			             
		
		Jf.setVisible(true);		
		MJp.setG();
	}

	public static void main(String[] args) 
	{	
		new MySW("C:\\Users\\chend\\Desktop\\MySW\\HUSTMap.txt");
	}
}