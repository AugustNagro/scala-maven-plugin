
/*
 * Copyright 2011-2020 scala-maven-plugin project (https://davidb.github.io/scala-maven-plugin/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package scala_maven;

import java.net.URL;
import java.net.URLClassLoader;

public class Scala3CompilerLoader extends URLClassLoader {
  private final ClassLoader sbtLoader;

  public Scala3CompilerLoader(URL[] urls, ClassLoader sbtLoader) {
    super(urls, null);
    this.sbtLoader = sbtLoader;
  }

  @Override
  public Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
    if (className.startsWith("xsbti.")) {
      // We can't use the loadClass overload with two arguments because it's
      // protected, but we can do the same by hand (the classloader instance
      // from which we call resolveClass does not matter).
      Class<?> c = sbtLoader.loadClass(className);
      if (resolve) resolveClass(c);
      return c;
    } else {
      return super.loadClass(className, resolve);
    }
  }
}