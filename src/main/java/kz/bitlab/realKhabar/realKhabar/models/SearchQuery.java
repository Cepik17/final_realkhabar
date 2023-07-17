package kz.bitlab.realKhabar.realKhabar.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchQuery {
    private String query;
    private boolean searchInText;
    private boolean searchInTitle;
    private boolean searchByAuthor;
}
