package com.wso2telco.services.dep.sandbox.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.wso2telco.services.dep.sandbox.dao.GenaricDAO;
import com.wso2telco.services.dep.sandbox.dao.model.domain.ManageNumber;
import com.wso2telco.services.dep.sandbox.dao.model.domain.SenderAddress;
import com.wso2telco.services.dep.sandbox.dao.model.domain.User;

class HibernateCommonDAO extends AbstractDAO implements GenaricDAO {

	public boolean isWhiteListedSenderAddress(int userId, String shortCode){
		
		SenderAddress senderAddress = null;
		
		Session session = getSession();
		
		senderAddress = (SenderAddress) session.createQuery("from SenderAddress where user.id = ? and shortCode = ?").setInteger(0, userId).setString(1, shortCode).uniqueResult();
		if (senderAddress != null) {
			
            return true;
        }
		
		return false;
	}
	
	 public User getUser(String username) {

	        Session sess = getSession();
	        User usr = null;
	        try {
	            usr = (User) sess.createQuery("from User where userName = ?").setString(0, username).uniqueResult();
	            if (usr == null) {
	                throw new Exception("User Not Found");
	            }

	        } catch (Exception e) {
	            //LOG.error("[ApplicationManager], RemveInbound + " + "Error " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            sess.close();
	        }

	        return usr;
	    }
	 
	 public List<ManageNumber>  getWhitelisted(int userid, List numbers) {

			Session sess = getSession();

			List<ManageNumber> whitelisted = null;
			try {
				Query query =sess.createQuery("from ManageNumber where user.id = :userid and number  in(:numbers)");
				query.setParameter("userid", userid);
				query.setParameterList( "numbers", numbers);
				whitelisted=query.list();

			} catch (Exception e) {
				System.out.println("getUserWhitelist: " + e);
			} finally {
				sess.close();
			}
			return whitelisted;
		}
}
