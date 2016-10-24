/*
 * Copyright © 2016 hankai.ren, All rights reserved.
 *
 * http://www.hankai.ren
 */

package ren.hankai.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提供一些公用便利方法的控制器基类
 *
 * @author hankai
 * @version 1.0
 * @since Aug 17, 2016 1:36:34 PM
 */
public abstract class AbstractController {

  protected static Logger logger = null;

  public AbstractController() {
    logger = LoggerFactory.getLogger(this.getClass());
  }
}
