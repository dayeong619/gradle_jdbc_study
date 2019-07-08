package gradle_jdbc_study.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gradle_jdbc_study.dao.DepartmentDao;
import gradle_jdbc_study.daoimpl.DepartmentDaoImpl;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.ui.content.PanelDepartment;
import gradle_jdbc_study.ui.list.DepartmentList;

@SuppressWarnings("serial")
public class DepartmentFrameUI extends AbstractFrameUI<Department> {
	private DepartmentDao dao = new DepartmentDaoImpl();
	
	public DepartmentFrameUI() {
		reloadItemList();
		setTitle("부서관리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		setpContent();
	}

	@Override
	protected void reloadItemList() { //리스트 전부를 불러오는 함수
		itemLists = dao.selectDepartmentByAll();
	}
	
	@Override
	protected void getContentPanel() {
		pContent = new PanelDepartment();
		
		int nextDeptNo = getNextNo();
		pContent.getTfNo().setText(String.format("D%03d", nextDeptNo));
		pContent.getTfNo().setEditable(false);
	}

	@Override
	protected int getNextNo() {
		int nextDeptNo;
		if (itemLists.size()==0) {
			nextDeptNo = 0;
		}else {
			nextDeptNo = itemLists.get(itemLists.size()-1).getDeptNo();
		}
		return nextDeptNo+1;
	}

	@Override
	protected void actionContentUpdateUI() {
		Department dept = pList.getSelectedItem();
		pContent.setItem(dept);
		pContent.getTfNo().setEditable(false);
		btnAdd.setText("수정");
	}

	@Override
	protected void getListPanel() {
		pList = new DepartmentList("부서 목록");
		reloadItemList();
		pList.setItemList(itemLists);
		pList.reloadData();
	}

	@Override
	protected void actionPerformedBtnUpdate() {
		Department dept = pContent.getItem();
		int res = dao.updateDepartment(dept);
		if (res == -1) {
			JOptionPane.showMessageDialog(null, dept + "수정 실패");
			return;
		}
		JOptionPane.showMessageDialog(null, dept + "수정 되었습니다.");
		refreshList();
		btnAdd.setText("추가");
		pContent.clearComponent(getNextNo());
	}

	@Override
	protected void actionPerformedBtnAdd() {
		Department dept = pContent.getItem();
		int res = dao.insertDepartment(dept);
		if (res == -1) {
			JOptionPane.showMessageDialog(null, dept + "추가 실패");
			return;
		}
		JOptionPane.showMessageDialog(null, dept.getDeptName() + "추가 되었습니다.");
		refreshList();
		pContent.clearComponent(getNextNo());
	}

	@Override
	protected void actionPerformedBtnDelete() {
		Department dept = pList.getSelectedItem();
		int res = dao.deleteDepartment(dept);
		if (res == -1) {
			JOptionPane.showMessageDialog(null, dept + "삭제 실패");
			return;
		}
		JOptionPane.showMessageDialog(null, dept + "삭제 되었습니다.");
		refreshList();
	}
}
