
package ren.hankai.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * 系统运行时信息
 *
 * @author hankai
 * @version 1.0
 * @since Aug 12, 2016 10:27:55 AM
 */
public class RuntimeInfo {

  private final Properties props;

  public RuntimeInfo() {
    props = System.getProperties();
    props.putAll(System.getenv());
    try {
      InetAddress addr = InetAddress.getLocalHost();
      props.put("hostName", addr.getHostName());
      props.put("hostIp", addr.getHostAddress());
    } catch (UnknownHostException e) {
    }
  }

  public String getHostName() {
    return props.getProperty("hostName");
  }

  public String getIpAddress() {
    return props.getProperty("hostIp");
  }

  public String getJvmTotalMemory() {
    long mem = Runtime.getRuntime().totalMemory();
    mem = mem / 1024 / 1024;
    return String.format("%d MB", mem);
  }

  public String getJvmFreeMemory() {
    long mem = Runtime.getRuntime().freeMemory();
    mem = mem / 1024 / 1024;
    return String.format("%d MB", mem);
  }

  public String getJvmAvailableProcessors() {
    return Runtime.getRuntime().availableProcessors() + "";
  }

  public String getJavaVersion() {
    return props.getProperty("java.version");
  }

  public String getJavaVender() {
    return props.getProperty("java.vendor");
  }

  public String getJavaHome() {
    return props.getProperty("java.home");
  }

  public String getJvmSpecVersion() {
    return props.getProperty("java.vm.specification.version");
  }

  public String getJvmSpecVender() {
    return props.getProperty("java.vm.specification.vendor");
  }

  public String getJvmSpecName() {
    return props.getProperty("java.vm.specification.name");
  }

  public String getJvmVersion() {
    return props.getProperty("java.vm.version");
  }

  public String getJvmVender() {
    return props.getProperty("java.vm.vendor");
  }

  public String getJvmName() {
    return props.getProperty("java.vm.name");
  }

  public String getJreSpecVersion() {
    return props.getProperty("java.specification.version");
  }

  public String getJreSpecVender() {
    return props.getProperty("java.specification.vender");
  }

  public String getJreSpecName() {
    return props.getProperty("java.specification.name");
  }

  public String getOsName() {
    return props.getProperty("os.name");
  }

  public String getOsArchitecture() {
    return props.getProperty("os.arch");
  }

  public String getOsVersion() {
    return props.getProperty("os.version");
  }
}
