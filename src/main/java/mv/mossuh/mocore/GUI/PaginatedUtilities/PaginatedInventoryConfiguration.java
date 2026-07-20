package mv.mossuh.mocore.GUI.PaginatedUtilities;

import mv.mossuh.mocore.ITEM.MoItemStack;
import mv.mossuh.mocore.UTILITIES.UsefulString;

public class PaginatedInventoryConfiguration {
    private String noPreviousPages = "";
    private String noAdditionalPages = "";
    private String previousPage = "";
    private String currentPage = "";
    private String nextPage = "";
    private MoItemStack backItem = new MoItemStack(null, null, null);
    private MoItemStack nextItem = new MoItemStack(null, null, null);
    private int backItemSlot = 0;
    private int nextItemSlot = 8;

    public PaginatedInventoryConfiguration(String noPreviousPages, String noAdditionalPages, String previousPage, String currentPage, String nextPage, MoItemStack backItem, MoItemStack nextItem, Integer backItemSlot, Integer nextItemSlot) {
        if (noPreviousPages != null) { this.noPreviousPages = UsefulString.get(noPreviousPages).hex().apply(); }
        if (noAdditionalPages != null) { this.noAdditionalPages = UsefulString.get(noAdditionalPages).hex().apply(); }
        if (previousPage != null) { this.previousPage = UsefulString.get(previousPage).hex().apply(); }
        if (currentPage != null) { this.currentPage = UsefulString.get(currentPage).hex().apply(); }
        if (nextPage != null) { this.nextPage = UsefulString.get(nextPage).hex().apply(); }
        if (backItem != null) { this.backItem = backItem; }
        if (nextItem != null) { this.nextItem = nextItem; }
        if (backItemSlot != null) { this.backItemSlot = backItemSlot; }
        if (nextItemSlot != null) { this.nextItemSlot = nextItemSlot; }
    }

    public PaginatedInventoryConfiguration(String noPreviousPages, String noAdditionalPages, String previousPage, String currentPage, String nextPage, MoItemStack backItem, MoItemStack nextItem) {
        if (noPreviousPages != null) { this.noPreviousPages = UsefulString.get(noPreviousPages).hex().apply(); }
        if (noAdditionalPages != null) { this.noAdditionalPages = UsefulString.get(noAdditionalPages).hex().apply(); }
        if (previousPage != null) { this.previousPage = UsefulString.get(previousPage).hex().apply(); }
        if (currentPage != null) { this.currentPage = UsefulString.get(currentPage).hex().apply(); }
        if (nextPage != null) { this.nextPage = UsefulString.get(nextPage).hex().apply(); }
        if (backItem != null) { this.backItem = backItem; }
        if (nextItem != null) { this.nextItem = nextItem; }
    }

    public PaginatedInventoryConfiguration() {

    }

    public int getBackItemSlot() { return backItemSlot; }
    public int getNextItemSlot() { return nextItemSlot; }

    public String getNoPreviousPages() {
        return this.noPreviousPages;
    }

    public void setNoPreviousPages(String noPreviousPages) {
        this.noPreviousPages = UsefulString.get(noPreviousPages).hex().apply();
    }

    public String getNoAdditionalPages() {
        return this.noAdditionalPages;
    }

    public void setNoAdditionalPages(String noAdditionalPages) {
        this.noAdditionalPages = UsefulString.get(noAdditionalPages).hex().apply();
    }

    public String getPreviousPage() {
        return this.previousPage;
    }

    public void setPreviousPage(String previousPage) {
        this.previousPage = UsefulString.get(previousPage).hex().apply();
    }

    public String getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = UsefulString.get(currentPage).hex().apply();
    }

    public String getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = UsefulString.get(nextPage).hex().apply();
    }

    public MoItemStack getBackItem() {
        return backItem;
    }
    public MoItemStack getNextItem(){
        return nextItem;
    }

    public void setNextItem(MoItemStack nextItem) {
        if (nextItem != null) { this.nextItem = nextItem; }
    }

    public void setBackItem(MoItemStack backItem) {
        if (backItem != null) { this.backItem = backItem; }
    }
}
