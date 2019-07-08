package gradle_jdbc_study.main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gradle_jdbc_study.ui.DepartmentFrameUI;
import gradle_jdbc_study.ui.EmployeeFrameUI;
import gradle_jdbc_study.ui.TitleFrameUI;

@SuppressWarnings("serial")
public class ErpApplication extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JButton btnEmp;
	private JButton btnDept;
	private JButton btnTitle;

	public ErpApplication() {
		initComponents();
	}
	private void initComponents() {
		setTitle("ERP 관리프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 110);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 10, 0));
		
		btnEmp = new JButton("사원관리");
		btnEmp.addActionListener(this);
		contentPane.add(btnEmp);
		
		btnDept = new JButton("부서관리");
		btnDept.addActionListener(this);
		contentPane.add(btnDept);
		
		btnTitle = new JButton("직책관리");
		btnTitle.addActionListener(this);
		contentPane.add(btnTitle);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnTitle) {
			actionPerformedBtnTitle(e);
		}
		if (e.getSource() == btnDept) {
			actionPerformedBtnDept(e);
		}
		if (e.getSource() == btnEmp) {
			actionPerformedBtnEmp(e);
		}
	}
	protected void actionPerformedBtnEmp(ActionEvent e) {
		EmployeeFrameUI frame = new EmployeeFrameUI();
		frame.setVisible(true);
	}
	protected void actionPerformedBtnDept(ActionEvent e) {
		DepartmentFrameUI frame = new DepartmentFrameUI();
		frame.setVisible(true);
	}
	protected void actionPerformedBtnTitle(ActionEvent e) {
		TitleFrameUI frame = new TitleFrameUI();
		frame.setVisible(true);
	}
}
