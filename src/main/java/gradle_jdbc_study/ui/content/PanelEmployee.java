package gradle_jdbc_study.ui.content;

import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;

@SuppressWarnings("serial")
public class PanelEmployee extends AbstractPanelContent<Employee>{
	private JTextField tfEmpNo;
	private JTextField tfEmpName;
	private JLabel lblTitle;
	private JComboBox<Title> cmbTitle;
	private JLabel lblSalary;
	private JSpinner spinSalary;
	private JLabel lblGender;
	private JPanel pGender;
	private JRadioButton rdbMale;
	private JRadioButton rdbFeMale;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblDept;
	private JComboBox<Department> cmbDept;
	private JLabel lblManager;
	private JComboBox<Employee> cmbMgn;
	private JLabel lblHireDate;
	private JTextField tfHireDate;
	
	private List<Title> titleList;
	private DefaultComboBoxModel<Title> titleModels;
	
	private List<Department> deptList;
	private DefaultComboBoxModel<Department> deptModels;
	
	private List<Employee> mgnList;
	private DefaultComboBoxModel<Employee> mgnModels;
	
	public PanelEmployee() {
		initComponents();
	}
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "사원정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 10, 10));
		
		JLabel lblEmpNo = new JLabel("사원 번호");
		lblEmpNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblEmpNo);
		
		tfEmpNo = new JTextField();
		add(tfEmpNo);
		tfEmpNo.setColumns(10);
		
		JLabel lblEmpName = new JLabel("사원명");
		lblEmpName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblEmpName);
		
		tfEmpName = new JTextField();
		tfEmpName.setColumns(10);
		add(tfEmpName);
		
		lblTitle = new JLabel("직책");
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTitle);
		
		cmbTitle = new JComboBox<Title>();
		add(cmbTitle);
		
		lblSalary = new JLabel("급여");
		lblSalary.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblSalary);
		
		spinSalary = new JSpinner();
		spinSalary.setModel(new SpinnerNumberModel(new Integer(1500000), null, null, new Integer(100000)));
		add(spinSalary);
		
		lblGender = new JLabel("성별");
		lblGender.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblGender);
		
		pGender = new JPanel();
		add(pGender);
		pGender.setLayout(new BoxLayout(pGender, BoxLayout.X_AXIS));
		
		rdbMale = new JRadioButton("남");
		rdbMale.setSelected(true);
		buttonGroup.add(rdbMale);
		pGender.add(rdbMale);
		
		rdbFeMale = new JRadioButton("여");
		buttonGroup.add(rdbFeMale);
		pGender.add(rdbFeMale);
		
		lblDept = new JLabel("부서");
		lblDept.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDept);
		
		cmbDept = new JComboBox<Department>();
		add(cmbDept);
		
		lblManager = new JLabel("직속상사");
		lblManager.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblManager);
		
		cmbMgn = new JComboBox<Employee>();
		add(cmbMgn);
		
		lblHireDate = new JLabel("입사일");
		lblHireDate.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblHireDate);
		
		tfHireDate = new JTextField();
		tfHireDate.setColumns(10);
		tfHireDate.setText(String.format("%tF", new Date()));
		add(tfHireDate);
	}
	
	public void setTitles(List<Title> titleLists) {
		this.titleList = titleLists;
		this.titleModels = new DefaultComboBoxModel<Title>(new Vector<Title>(titleLists));
		cmbTitle.setModel(titleModels);
		cmbTitle.setSelectedIndex(-1);
	}
	
	public void setDepartments(List<Department> deptLists) {
		this.deptList = deptLists;
		this.deptModels = new DefaultComboBoxModel<Department>(new Vector<Department>(deptLists));
		cmbDept.setModel(deptModels);
		cmbDept.setSelectedIndex(-1);
	}
	
	public void setManagements(List<Employee> mgnLists) {
		this.mgnList = mgnLists;
		if (mgnModels != null) {
			mgnModels.removeAllElements();
		}
		this.mgnModels = new DefaultComboBoxModel<Employee>(new Vector<Employee>(mgnLists));
		cmbMgn.setModel(mgnModels);
//		cmbMgn.setSelectedIndex(-1);
	}
	
	
	@Override
	public void setItem(Employee emp) {
		tfEmpNo.setText(String.format("E%06d", emp.getEmpNo()));
		tfEmpName.setText(emp.getEmpName());
		cmbTitle.setSelectedItem(emp.getTitle());
		cmbDept.setSelectedItem(emp.getDno());
		
		cmbMgn.setSelectedItem(emp.getManager());
		spinSalary.setValue(emp.getSalary());
		if(emp.isMale()) {
			rdbMale.setSelected(true);
		}else {
			rdbFeMale.setSelected(true);
		}
		
		tfHireDate.setText(String.format("%tF", emp.getHireDate()));
	}

	@Override
	public Employee getItem() {
		int empNo = Integer.parseInt(tfEmpNo.getText().trim().substring(1));
		String empName = tfEmpName.getText().trim();
		Title title = (Title) cmbTitle.getSelectedItem();
		Employee manager = (Employee) cmbMgn.getSelectedItem();
		int salary = (Integer)spinSalary.getValue();
		boolean isMale = rdbMale.isSelected()?true:false;
		Department dno = (Department) cmbDept.getSelectedItem();
		Date hireDate = null;
		try {
			hireDate = new SimpleDateFormat("yyyy-MM-dd").parse(tfHireDate.getText().trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Employee(empNo, empName, title, manager, salary, isMale, dno, hireDate);
	}

	@Override
	public void clearComponent(int nextNo) {
		System.out.println(nextNo);
		tfEmpNo.setText(String.format("E%6d", nextNo));
		tfEmpName.setText("");
		cmbTitle.setSelectedIndex(-1);;
		spinSalary.setValue(new Integer(1500000));;
		rdbMale.setSelected(true);
		cmbDept.setSelectedIndex(-1);
		mgnModels.removeAllElements();
		cmbMgn.setSelectedIndex(-1);
		tfHireDate.setText(String.format("%tF", new Date()));
	}

	@Override
	public JTextField getTfNo() {
		return tfEmpNo;
	}

	@Override
	public void setComponentAllEditable(boolean isEditable) {
		// TODO Auto-generated method stub
		
	}

	public JComboBox<Department> getCmbDept() {
		return cmbDept;
	}

	public JComboBox<Employee> getCmbMgn() {
		return cmbMgn;
	}

}
