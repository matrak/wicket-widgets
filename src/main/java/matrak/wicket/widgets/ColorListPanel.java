package matrak.wicket.widgets;

import java.util.ArrayList;
import java.util.List;

import matrak.model.color.Gradient;
import matrak.model.color.Gradient.Color;
import matrak.model.color.Gradient.ColorDistance;
import matrak.paging.Pagination;
import matrak.wicket.widgets.lists.ScrollListPanel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

public class ColorListPanel extends ScrollListPanel<List<Color>> {

	private static final long serialVersionUID = 1L;

	public ColorListPanel(String wicketId) {
		super(wicketId);
	}
	
	public ColorListPanel(String wicketId, int offset, boolean nested) {
		super(wicketId, offset, nested);
	}
	
	@Override
	protected Pagination<List<Color>> loadPagination(int offset) {
		ColorDistance coldist = ColorDistance.BIG;
		ColorDistance rowdist = ColorDistance.BIG;
		
		Pagination<List<Color>> pg = new Pagination<List<Color>>(offset, 1);
		List<Color> rowColors = Gradient.getColorRow(offset, coldist, rowdist);
		List<List<Color>> row = new ArrayList<List<Color>>(1);
		row.add(rowColors);
		pg.setItems(row, Gradient.getMaxColors(coldist, rowdist));
		
		return pg;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ColorListPanel nextPage(String wicketId, int offset, boolean nested) {
		return new ColorListPanel(wicketId, offset + 1, nested);
	}
	
	@Override
	protected void populateItem(ListItem<List<Color>> item, IModel<List<Color>> itemModel) {
		List<Color> colorsList = itemModel.getObject();
		item.add(new ListView<Color>("color", colorsList) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Color> colorItem) {
				Color color = colorItem.getModelObject();
				String colorStyle = String.format("background-color: #%s;", color.getHexCode());
				colorItem.add(AttributeModifier.replace("style", colorStyle));
				colorItem.add(new Label("value", color.getHexCode()));
			}
		});
	}
}
