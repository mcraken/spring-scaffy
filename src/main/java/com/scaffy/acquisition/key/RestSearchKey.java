/**
 * @author Sherief Shawky(raken123@yahoo.com)
 */
package com.scaffy.acquisition.key;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.scaffy.acquisition.exception.InvalidCriteriaSyntaxException;

/**
 * @author Sherief Shawky(raken123@yahoo.com)
 *
 */
public class RestSearchKey {

	public static final String SEACRH_KEY = "search_key";

	private int start;
	private int count;
	private String select;
	@NotNull
	private String resourceName;
	private String queryName;
	@Valid
	private ArrayList<RestCriteria> criterias;

	private boolean selectionRequired;

	private String orderBy;
	
	private boolean desc;
	
	public RestSearchKey(){

	}

	public RestSearchKey(String resourceName, RestCriteria...crts){

		this.resourceName = resourceName;

		this.criterias = new ArrayList<RestCriteria>();

		for(RestCriteria crt:crts)
			this.criterias.add(crt);
	}

	public boolean hasOrders() {
		return orderBy != null;
	}
	
	public boolean isDesc() {
		return desc;
	}
	
	public String[] getOrders() {
		return orderBy.split(",");
	}
	
	/**
	 * @return the selectionRequired
	 */
	public boolean isSelectionRequired() {
		return selectionRequired;
	}

	/**
	 * @return the queryName
	 */
	public String getQueryName() {
		return queryName;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<RestCriteria> getCriterias() {
		return criterias;
	}

	public void setCriterias(ArrayList<RestCriteria> criterias) {
		this.criterias = criterias;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void parseAllCriterias() throws InvalidCriteriaSyntaxException{

		if(criterias != null)
		for(RestCriteria crt : criterias)
			try {

				crt.parse();

			} catch (Exception e) {

				throw new InvalidCriteriaSyntaxException(e);
			}

	}

	public String hasProperty(String name){

		for(RestCriteria crt : criterias){
			if(crt.propertyNameEquals(name))
				return crt.getCrudeValue();
		}

		return null;
	}

	public Iterator<RestCriteria> criteriasIterator(){

		if(criterias == null)
			return null;
		
		return criterias.iterator();
	}

	public String[] parseColumns(){
		return select.split(",");
	}

	public Iterator<RestProjection> projectionIterator(){

		if(select == null)
			return null;

		List<RestProjection> projections = new ArrayList<RestProjection>();

		StringTokenizer projectionList = new StringTokenizer(select, ";");

		while(projectionList.hasMoreTokens()){

			projections.add(new RestProjection(projectionList.nextToken()));
		}

		return projections.iterator();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object paramObject) {

		RestSearchKey other = (RestSearchKey) paramObject;

		if(this == other)
			return true;

		EqualsBuilder equalsBuilder = new EqualsBuilder();
		
		equalsBuilder.append(this.resourceName, other.resourceName)
		.append(this.start, other.start)
		.append(this.count, other.count);
		
		if(this.criterias !=null && other.criterias != null)
			equalsBuilder.append(this.criterias.toArray(), other.criterias.toArray());
		else if(this.criterias != other.criterias)
			return false;
		
		if(hasOrders()){
			equalsBuilder.append(this.orderBy, other.orderBy)
			.append(this.desc, other.desc);
		}
		
		return equalsBuilder.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		HashCodeBuilder hashCodeBuilder  = new HashCodeBuilder(); 
		
		hashCodeBuilder.append(resourceName)
		.append(start)
		.append(count);
		
		if(criterias != null)
			hashCodeBuilder.append(criterias.toArray());
	
		if(hasOrders()){
			hashCodeBuilder.append(orderBy);
			hashCodeBuilder.append(desc);
		}
		
		return	hashCodeBuilder.toHashCode();
	}
}
