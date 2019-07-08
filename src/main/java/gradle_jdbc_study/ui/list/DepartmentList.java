package gradle_jdbc_study.ui.list;

import javax.swing.SwingConstants;

import gradle_jdbc_study.dto.Department;

@SuppressWarnings("serial")
public class DepartmentList extends AbstractListPanel<Department> {

	public DepartmentList(String title) {
		super(title);
	}

	@Override
	protected void tableAlignmentAndWidth() {
		// 부서번호, 부서명은 가운데 정렬
		tableCellAlignment(SwingConstants.CENTER, 0, 1);
		// 위치(층)은 우측 정렬
		tableCellAlignment(SwingConstants.RIGHT, 2);
		// 부서번호, 부서명, 위치 의 폭을 (100, 200, 70)으로 가능하면 설정
		tableSetWidth(100, 200, 70);
	}

	@Override
	protected Object[] toArray(int idx) {
		Department dept = itemList.get(idx);
		return dept.toArray();
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] { "부서번호", "부서명", "위치(층)" };
	}

}
