package matrak.paging;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int offset;
	private int itemsPerPage;
	
	private long maxItems;
	private List<T> items;
	
	public Pagination(int offset, int itemsPerPage) {
		this.offset = offset;
		this.itemsPerPage = itemsPerPage;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	public void setItems(List<T> items, long maxItems) {
		this.items = items;
		this.maxItems = maxItems;
	}
	
	public List<T> getItems() {
		return items;
	}
	
	public long getMaxItems() {
		return maxItems;
	}
	
	public boolean lastPage() {
		return (offset + itemsPerPage) >= maxItems;
	}
}
