
package ren.hankai.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ren.hankai.config.Route;
import ren.hankai.util.RuntimeInfo;

/**
 * 默认控制器
 *
 * @author hankai
 * @version 1.0
 * @since Apr 1, 2016 9:56:54 AM
 */
@Controller
public class DefaultController {

  /**
   * 后台首页
   *
   * @return
   * @author hankai
   * @since Jun 21, 2016 1:14:55 PM
   */
  @RequestMapping(Route.BACKGROUND_PREFIX)
  public ModelAndView adminIndex() {
    return new ModelAndView("admin/sample");
  }

  /**
   * 前台首页
   *
   * @return
   * @author hankai
   * @since Jun 21, 2016 1:15:06 PM
   */
  @RequestMapping(value = {"/", Route.FG_SAMPLE})
  public ModelAndView foregroundIndex() {
    ModelAndView mav = new ModelAndView("site/sample");
    return mav;
  }

  /**
   * 显示系统运行参数
   * 
   * @return
   * @author hankai
   * @since Aug 12, 2016 11:52:56 AM
   */
  @RequestMapping(Route.BG_SYS_SETTINGS)
  public ModelAndView systemSettings() {
    ModelAndView mav = new ModelAndView("admin/settings");
    mav.addObject("runtime", new RuntimeInfo());
    return mav;
  }
}
