package gradle_jdbc_study.ui.content;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import gradle_jdbc_study.dto.Department;

@SuppressWarnings("serial")
public class PanelDepartment extends AbstractPanelContent<Department>{
	private JTextField tfDeptNo;
	private JTextField tfDeptName;
	private JTextField tfFloor;

	public PanelDepartment() {
		initComponents();
	}
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "부서정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 10, 10));
		
		JLabel lblDeptNo = new JLabel("부서 번호");
		lblDeptNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDeptNo);
		
		tfDeptNo = new JTextField();
		add(tfDeptNo);
		tfDeptNo.setColumns(10);
		
		JLabel lblDeptName = new JLabel("부서명");
		lblDeptName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDeptName);
		
		tfDeptName = new JTextField();
		tfDeptName.setColumns(10);
		add(tfDeptName);
		
		JLabel lblFloor = new JLabel("위치(층)");
		lblFloor.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblFloor);
		
		tfFloor = new JTextField();
		tfFloor.setColumns(10);
		add(tfFloor);
	}
	
	
	public void clearTextField() { //텍스트필드를 비움
		tfDeptNo.setText("");
		tfDeptName.setText("");
		tfFloor.setText("");
		if (!tfDeptNo.isEditable()) {
			tfDeptNo.setEditable(true);
		}
	}
	
	@Override
	public void setItem(Department dept) { //Department에서 가져온 정보를 셋팅
		tfDeptNo.setText(String.format("D%03d", dept.getDeptNo())); 
		tfDeptName.setText(dept.getDeptName());
		tfFloor.setText(String.valueOf(dept.getFloor())); //Floor는 인트라서 스트링으로 형변환	
	}
	
	@Override
	public Department getItem() { //Department에서 가져오기
		int deptNo = Integer.parseInt(tfDeptNo.getText().trim().substring(1)); //deptNo에서 첫글자 한자리 떼고 스트링으로 가져오기
		String deptName = tfDeptName.getText().trim(); //deptName을 가져오기
		int floor = Integer.parseInt(tfFloor.getText().trim()); //tfFloor 숫자를 스트링으로 변환
		return new Department(deptNo, deptName, floor); //새로 정의한 department를 리턴해줌. (전부 다 스트링임)
	}
	
	@Override
	public void clearComponent(int nextNo) { //DeptNo에 nextNo가 들어가고 비활성화됨.
		tfDeptNo.setText(String.format("D%03d", nextNo));
		tfDeptName.setText("");
		tfFloor.setText("");
		tfDeptNo.setEditable(false); //비활성화
		/*
		 * if (!tfDeptNo.isEditable()) { tfDeptNo.setEditable(true); }
		 */	
	}
	
	@Override
	public JTextField getTfNo() { // thDeptNo를 리턴하는 함수
		return tfDeptNo;
	}
	
	@Override
	public void setComponentAllEditable(boolean isEditable) { //전부 다 비활성화
		tfDeptNo.setEditable(isEditable);
		tfDeptName.setEditable(isEditable);
		tfFloor.setEditable(isEditable);
	}

}







