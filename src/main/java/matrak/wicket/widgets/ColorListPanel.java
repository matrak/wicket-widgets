package matrak.wicket.widgets;

import matrak.model.color.Gradient;
import matrak.model.color.Gradient.Color;
import matrak.model.color.Gradient.ColorDistance;
import matrak.paging.Pagination;
import matrak.wicket.widgets.lists.ScrollListPanel;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;

public class ColorListPanel extends ScrollListPanel<Color> {

	private static final long serialVersionUID = 1L;

	public ColorListPanel(String wicketId) {
		super(wicketId);
	}
	
	public ColorListPanel(String wicketId, int offset, boolean nested) {
		super(wicketId, offset, nested);
	}
	
	@Override
	protected Pagination<Color> loadPagination(int offset) {
		return Gradient.getColorRow(offset, ColorDistance.BIG, ColorDistance.BIG);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ColorListPanel nextPage(String wicketId, int offset, boolean nested) {
		return new ColorListPanel(wicketId, offset, nested);
	}
	
	@Override
	protected void populateItem(ListItem<Color> item, IModel<Color> itemModel) {

	}
}
