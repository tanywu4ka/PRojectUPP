package core;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.codec.digest.DigestUtils;

import entity.City;
import entity.Road;
import entity.RouteName;
import entity.RouteStop;
import entity.StaffMember;
import entity.Train;
import entity.TrainStation;
import entity.Wagon;

public enum DBApi {
	
	GET;
	
	private static final String PERSISTENCE_UNIT_NAME = "ticketKassa";
	private EntityManagerFactory factory;
	private EntityManager em;
	
	//lets cache get requests
	private Map<String, Object> dbCache = new HashMap<>();
	
	DBApi() {
		System.out.println("start connecting");
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
		System.out.println("connected");
	}
	
	
	
	public List<StaffMember> getAllStaff() {
		List<StaffMember> staffList = (List<StaffMember>)dbCache.get("staffList");
		
		if (staffList == null) {
			Query q = em.createQuery("SELECT e FROM StaffMember e ");
			staffList = q.getResultList();
			dbCache.put("staffList", staffList);
		} else {
			//System.out.println("Showing cached staff list");
		}

		return staffList;
	}
	
	
	
	public StaffMember getStaffMember(String login, String pass) {
		Query q = em.createQuery("SELECT a FROM StaffMember a WHERE a.staff_login=:arg_login AND a.staff_pass=:arg_pass").setMaxResults(1);
		q.setParameter("arg_login", login);
		q.setParameter("arg_pass", DigestUtils.md5Hex(pass));
		
		if (q.getResultList().size() > 0) {
			return (StaffMember) q.getResultList().get(0);
		}
		
		return null;
	}
	
	public List<City> getCities() {
		List<City> cities = (List<City>)dbCache.get("cityList");
		
		if (cities == null) {
			Query q = em.createQuery("SELECT e FROM City e ");
			cities = q.getResultList();
			dbCache.put("cityList", cities);
		} else {
			//System.out.println("Showing cached cities");
		}

		return cities;
	}
	
	public List<Road> getRoads() {
		List<Road> roads = (List<Road>)dbCache.get("roadList");
		
		if (roads == null) {
			Query q = em.createQuery("SELECT e FROM Road e ");
			roads = q.getResultList();
			dbCache.put("roadList", roads);
		} else {
			//System.out.println("Showing cached roads");
		}
		
		return roads;
	}
	
	//======= Route names
	public int addRouteName(String name) {
		int res;
		em.getTransaction().begin();
		RouteName rName = new RouteName(name);
		em.persist(rName);
		em.flush();
		res = rName.getId();
		em.getTransaction().commit();
		
		//invalidate name cache
		dbCache.put("routeNameList", null);
		
		return res;
	}
	
	public List<RouteName> getRouteNames() {
		List<RouteName> routeNames = (List<RouteName>)dbCache.get("routeNameList");
		
		if (routeNames == null) {
			Query q = em.createQuery("SELECT e FROM RouteName e ");
			routeNames = q.getResultList();
			dbCache.put("routeNameList", routeNames);
		} else {
			//System.out.println("Showing cached route names");
		}
		
		return routeNames;
	}
	
	// ========
	
	// ======== Route stops
	
	public void AddRouteStop(int routeId, int orderId, int cityId, boolean isStop) {
		em.getTransaction().begin();
		RouteStop rStop = new RouteStop(routeId, orderId, cityId, isStop);
		em.persist(rStop);
		em.getTransaction().commit();
	}
	
	public List<RouteStop> getRouteStops(RouteName route) {
		Query q = em.createQuery("SELECT e FROM RouteStop e WHERE e.route_id=:arg_routeId");
		q.setParameter("arg_routeId", route.getId());
		List<RouteStop> routeNames = q.getResultList();
		
		return routeNames;
	}
	
	// ========
	
	public City getCityById(int id) {
		//this is faster then sql query 
		//as long as connection takes a while and ammount is small
		
		City res = null;
		List<City> citiyList = getCities();
		for(City c: citiyList) {
			if (c.getId() == id) {
				res = c;
				break;
			}
		}
		
		return res;
	}
	
	public String getRouteNameById(int id) {
		//this is faster then sql query 
		//as long as connection takes a while and ammount is small
		
		String res = (String)dbCache.get("route" + id);
		
		if (res == null) {
			List<RouteName> routeNames = getRouteNames();
			for(RouteName name: routeNames) {
				if (name.getId() == id) {
					res = name.getRoute_name();
					dbCache.put("route" + id, res);
					break;
				}
			}
		}
		
		
		
		return res;
	}
	
	
	public List<Train> getAllTrains() {
		List<Train> trianList = (List<Train>)dbCache.get("trainList");
		
		if (trianList == null) {
			Query q = em.createQuery("SELECT e FROM Train e ");
			trianList = q.getResultList();
			dbCache.put("trainList", trianList);
		} else {
			//System.out.println("Showing cached staff list");
		}

		return trianList;
	}
	
	public int addTrain(boolean [] days, String hours, String mins, int routeId) {
		int res;
		em.getTransaction().begin();
		Train train = new Train(days, hours, mins, routeId);
		em.persist(train);
		em.flush();
		res = train.getId();
		em.getTransaction().commit();
		
		//invalidate name cache
		dbCache.put("trainList", null);
		
		return res;
	}
	
	public void addTrainStaiton(int order, String hour, String min, String cost, int trainId, int cityId) {
		em.getTransaction().begin();
		TrainStation station = new TrainStation(order, hour, min, cost, trainId, cityId);
		em.persist(station);
		em.getTransaction().commit();
	}
	
	public void addWagon(Wagon w) {
		em.getTransaction().begin();
		em.persist(w);
		em.getTransaction().commit();
	}

	
	public void dummy() {
		//this is called to create instance of db
	}

	
	
}












