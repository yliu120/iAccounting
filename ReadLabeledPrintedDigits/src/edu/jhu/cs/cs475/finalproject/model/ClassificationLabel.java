package edu.jhu.cs.cs475.finalproject.model;

import java.io.Serializable;

public class ClassificationLabel extends Label implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer label;
	
	public Integer getLabel() {
		return label;
	}
	
	public ClassificationLabel(Integer label) {
		// TODO Auto-generated constructor stub
		/* @author Yunlong Liu
		 * @date
		 */
		this.label = label ;
	}
	
	public Boolean equals( ClassificationLabel label ) {
		// TODO Auto-generated method stub
		if ( label.getLabel().equals( this.label ) ) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		/* @author Yunlong Liu
		 * @date
		 */
		return label.toString();
	}

}
