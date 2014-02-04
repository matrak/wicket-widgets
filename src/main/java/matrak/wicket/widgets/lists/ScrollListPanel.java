package matrak.wicket.widgets.lists;


import java.util.List;

import matrak.paging.Pagination;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

public abstract class ScrollListPanel<T> extends GenericPanel<Pagination<T>> {
	
	private static final long serialVersionUID = 1L;

	private boolean nested = false;
	protected IModel<Integer> offsetModel;
	
	public ScrollListPanel(String wicketId) {
		this(wicketId, 0, false);
	}
	
	public ScrollListPanel(String wicketId, int offset, boolean nested) {
		super(wicketId);
		
		this.nested = nested;
		this.offsetModel = new Model<Integer>(offset);
		this.setModel(new PaginationModel());
		
		IModel<List<T>> listModel = new PropertyModel<List<T>>(getModel(), "items");
		add(new ListView<T>("list", listModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<T> item) {
				ScrollListPanel.this.populateItem(item, item.getModel());
			}
			
		});
		
		final WebMarkupContainer nextPageContainer = new WebMarkupContainer("nextPageContainer");
		nextPageContainer.setOutputMarkupId(true);
		add(nextPageContainer);
		
		final WebMarkupContainer nextPageLinkContainer = new WebMarkupContainer("nextPageLinkContainer");
		AjaxLink<Void> nextPageLink = new AjaxLink<Void>("nextPageLink") 
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) 
			{
				int offset = offsetModel.getObject();
				ScrollListPanel<T> nextPage = nextPage("nextPageContainer", offset, true);
				nextPageContainer.replaceWith(nextPage);
				getParent().setVisibilityAllowed(false);
				target.add(getParent(), nextPage);
			}

			@Override
			protected void onConfigure() 
			{
				// Prevent previous (hidden) ScrollListPanel link from query the LDM.
				if (getParent().isVisibilityAllowed()) {
					Pagination<T> listing = ScrollListPanel.this.getModelObject();
					getParent().setVisibilityAllowed(!listing.lastPage());
				}
				super.onConfigure();
			}
		};
		nextPageLink.setOutputMarkupId(true);
		nextPageLink.setBody(new ResourceModel("scrolllist.nextpage.button"));
		nextPageLinkContainer.add(nextPageLink);
		nextPageLinkContainer.setOutputMarkupId(true);
		add(nextPageLinkContainer);		
	}
	
	private class PaginationModel extends LoadableDetachableModel<Pagination<T>> {
		
		private static final long serialVersionUID = 1L;

		@Override
		protected Pagination<T> load() {
			int offset = offsetModel.getObject();
			return loadPagination(offset);
		}
	}
	
	
	protected abstract void populateItem(ListItem<T> item, IModel<T> itemModel);
	protected abstract <S extends ScrollListPanel<T>> S nextPage(String wicketId, int lastOffset, boolean nested);
	protected abstract Pagination<T> loadPagination(int offset);
}
