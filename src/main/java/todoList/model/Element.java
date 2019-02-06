package todoList.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Element {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String textField;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public Set<Category> getCategory() {
		return category;
	}

	public void setCategory(Set<Category> category) {
		this.category = category;
	}

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "element_category",
			joinColumns = { @JoinColumn(name = "element_id") },
			inverseJoinColumns = { @JoinColumn(name = "category_id") })
	private Set<Category> category = new HashSet<>();

	public boolean hasCategory(Integer categoryid) {
		return categoryid == null
				|| category.stream()
				.anyMatch(c -> c.getId().equals(categoryid));
	}
}

