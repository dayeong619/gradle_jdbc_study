package gradle_jdbc_study.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.service.EmployeeDaoService;
import gradle_jdbc_study.ui.content.PanelEmployee;
import gradle_jdbc_study.ui.list.EmployeeList;

@SuppressWarnings("serial")
public class EmployeeFrameUI extends AbstractFrameUI<Employee> implements ItemListener {
	private EmployeeDaoService service = new EmployeeDaoService();
	private JComboBox<Department> cmbDept;
//	private JComboBox<Employee> cmbMgn;
	
	public EmployeeFrameUI() {
		reloadItemList();
		setTitle("사원관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 650);
		setpContent();
	}

	@Override
	protected void getContentPanel() {
		pContent = new PanelEmployee();
		
		int nextDeptNo = getNextNo();
		
		pContent.getTfNo().setText(String.format("E%06d", nextDeptNo));
		pContent.getTfNo().setEditable(false);
		
		((PanelEmployee)pContent).setTitles(service.selectTitleByAll());
		((PanelEmployee)pContent).setDepartments(service.selectDepartmentByAll());

		cmbDept = ((PanelEmployee)pContent).getCmbDept();
		cmbDept.addItemListener(this);
		
//		cmbMgn = ((PanelEmployee)pContent).getCmbMgn();
		
	}

	@Override
	protected void getListPanel() {
		pList = new EmployeeList("사원 목록");
		reloadItemList();
		pList.setItemList(itemLists);
		pList.reloadData();
	}

	@Override
	protected int getNextNo() {
		int nextDeptNo;
		reloadItemList();
		if (itemLists.size()==0) {
			nextDeptNo = Integer.parseInt(String.format("%s%03d",String.format("%tY", new Date()).substring(1) ,0));
		}else {
			nextDeptNo = itemLists.get(itemLists.size()-1).getEmpNo();
		}
		return nextDeptNo+1;
	}

	@Override
	protected void reloadItemList() {
		itemLists = service.selectEmployeeByAll();
	}

	@Override
	protected void actionContentUpdateUI() {
		Employee selEmp = pList.getSelectedItem();
		pContent.setItem(selEmp);
		pContent.getTfNo().setEditable(false);
		btnAdd.setText("수정");	
	}

	@Override
	protected void actionPerformedBtnUpdate() {
		Employee upEmp = pContent.getItem();
		int res = service.updateEmployee(upEmp);
		if (res == -1) {
			JOptionPane.showMessageDialog(null, String.format("%s(%s) 수정 실패", upEmp.getEmpName(), upEmp.getEmpNo()));
			return;
		}
		JOptionPane.showMessageDialog(null, String.format("%s(%s) 수정 되었습니다.", upEmp.getEmpName(), upEmp.getEmpNo()));
		pContent.clearComponent(getNextNo());
		
		reloadItemList();
		pList.setItemList(itemLists);
		pList.reloadData();
		btnAdd.setText("추가");
	}

	@Override
	protected void actionPerformedBtnAdd() {
		Employee newEmp = pContent.getItem();
		int res = service.insertEmployee(newEmp);
		if (res != -1) {
			JOptionPane.showMessageDialog(null, String.format("%s 사원이 추가되었습니다.", newEmp.getEmpName()));
			pContent.clearComponent(getNextNo());
		}
		reloadItemList();
		pList.setItemList(itemLists);
		pList.reloadData();
	}

	@Override
	protected void actionPerformedBtnDelete() {
		Employee delEmp = pList.getSelectedItem();
		int res = service.deleteEmployee(delEmp);
		if (res != -1) {
			JOptionPane.showMessageDialog(null, String.format("%s 사원이 삭제되었습니다.", delEmp.getEmpName()));
		}
		reloadItemList();
		pList.setItemList(itemLists);
		pList.reloadData();
		revalidate();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			Department selDept = (Department) cmbDept.getSelectedItem();
			List<Employee> mgnList = service.selectEmployeeByDno(selDept.getDeptNo());
			if (mgnList == null) {
				mgnList = new ArrayList<Employee>();
			}
			((PanelEmployee)pContent).setManagements(mgnList);
		}		
	}

}
