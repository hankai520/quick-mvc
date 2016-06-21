
package ren.hankai.persist;

import org.springframework.stereotype.Service;

import ren.hankai.persist.model.User;
import ren.hankai.persist.util.JPABasedDAO;

/**
 * 用户业务
 *
 * @author hankai
 * @version 1.0
 * @since Jul 17, 2015 9:21:02 AM
 */
@Service
public class UserService extends JPABasedDAO<User> {
}
