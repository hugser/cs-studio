package org.hibernate.jmx;

/**
 * {@inheritDoc}
 *
 * @author Steve Ebersole
 */
public class Entity {
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
