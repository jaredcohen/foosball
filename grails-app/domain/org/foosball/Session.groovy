package org.foosball

class Session {

	int id;
	String description;
	
	static transients = ['currentSession']
	
    static constraints = {
    }
	
	public static Integer getCurrentSession() {
		Session.createCriteria().get {
			projections { max "id" }
		} as Integer
	}
	
}
