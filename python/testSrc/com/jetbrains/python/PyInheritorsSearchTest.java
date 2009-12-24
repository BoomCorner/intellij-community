package com.jetbrains.python;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.search.ProjectScope;
import com.intellij.psi.stubs.StubIndex;
import com.jetbrains.python.fixtures.PyLightFixtureTestCase;
import com.jetbrains.python.psi.PyClass;
import com.jetbrains.python.psi.search.PyClassInheritorsSearch;
import com.jetbrains.python.psi.stubs.PyClassNameIndex;

import java.util.Collection;

/**
 * @author yole
 */
public class PyInheritorsSearchTest extends PyLightFixtureTestCase {
  public void testSimple() throws Exception {
    setupProject();
    final PyClass pyClass = findClass("A");
    Collection<PyClass> inheritors = PyClassInheritorsSearch.search(pyClass, false).findAll();
    assertEquals(2, inheritors.size());
  }

  public void testDeep() throws Exception {
    setupProject();
    final PyClass pyClass = findClass("A");
    Collection<PyClass> inheritors = PyClassInheritorsSearch.search(pyClass, true).findAll();
    assertEquals(2, inheritors.size());
  }

  public void testDotted() throws Exception {
    setupProject();
    final PyClass pyClass = findClass("A");
    Collection<PyClass> inheritors = PyClassInheritorsSearch.search(pyClass, true).findAll();
    assertEquals(1, inheritors.size());
  }

  private void setupProject() throws Exception {
    String testName = getTestName(true);
    myFixture.copyDirectoryToProject(testName, "");
    PsiDocumentManager.getInstance(myFixture.getProject()).commitAllDocuments();
  }

  private PyClass findClass(final String name) {
    final Project project = myFixture.getProject();
    final Collection<PyClass> classes = StubIndex.getInstance().get(PyClassNameIndex.KEY, name, project,
                                                                    ProjectScope.getProjectScope(project));
    assertEquals(1, classes.size());
    return classes.iterator().next();
  }

  @Override
  protected String getTestDataPath() {
    return PythonTestUtil.getTestDataPath() + "/inheritors/";
  }
}
