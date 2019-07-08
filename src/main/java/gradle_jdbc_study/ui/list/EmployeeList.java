package gradle_jdbc_study.ui.list;

import javax.swing.SwingConstants;

import gradle_jdbc_study.dto.Employee;

@SuppressWarnings("serial")
public class EmployeeList extends AbstractListPanel<Employee> {

	public EmployeeList(String title) {
		super(title);
	}

	@Override
	protected void tableAlignmentAndWidth() {
		// 부서번호, 부서명은 가운데 정렬
		tableCellAlignment(SwingConstants.CENTER, 0, 1, 2, 3, 5, 6, 7);
		// 위치(층)은 우측 정렬
		tableCellAlignment(SwingConstants.RIGHT, 4);
		// 부서번호, 부서명, 위치 의 폭을 (100, 200, 70)으로 가능하면 설정
		tableSetWidth(100, 80, 70, 100, 100, 70, 120);
	}

	@Override
	protected Object[] toArray(int idx) {
		return itemList.get(idx).toArray();
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return new String[] { "번호", "사원명", "직책", "직속상사", "급여", "성별", "부서", "입사일" };
	}

}
