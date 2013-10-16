package com.base.others;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyJApplet extends JApplet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		// ��init�����н�������htmlҳ���ϵĲ���
		//String message = getParameter("MESSAGE");
		add(new JLabel("welcome to touch's blog", JLabel.CENTER));
	}

	// ��main��������JApplet
	public static void main(String[] args) {
		JFrame frame = new JFrame("Applet is in the frame");
		MyJApplet myJApplet = new MyJApplet();
		// main�����ﴴ��һ�����������applet��applet��������ʱ��
		// Ҫ��ɲ��������ֶ�����init��start����
		frame.add(myJApplet, BorderLayout.CENTER);
		myJApplet.init();

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}
