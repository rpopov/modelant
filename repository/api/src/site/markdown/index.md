Lookup A Repository Implementation
----------------------------------

As of **\[A4\]**, each program looks up the Repository API Implementation, either of the default modelat repository implementation or any other implementation, provided in classpath, using the idiom:

```
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
...
modelRepository = ModelRepositoryFactory.construct(storage file);
```
Nevertheless, once the repository is instantiated, it must be closed when done and its resources have to be released. Therefore apply the following idiom of the repository's lifecycle:

```
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
...
  boolean commit;

  modelRepository = ModelRepositoryFactory.construct(storage file);
  try {
    modelRepository.beginTransaction();
    try {
      ..
      manipulate
      ...
      commit = true;
    } catch (Exception ex) { // rollback
      commit = false;
    } finally {
      modelRepository.endTransaction(commit);
    }
  } finally {
    modelRepository.shutdown()
  }
```
Provide the Metamodel Repository implementation as a dependency in the classpath of the implementation.

Integrate a Repository Implementation
-------------------------------------

As of **\[A4\]**, any implementation of the Repository API, including the [default one](../repostory.impl) should follw the idiom below in order to be looked up:

* The implementation of the Metamodel Repository should provide the class name of its implementation in the file:
```
META-INF/services/net.mdatools.modelant.repository.api.ModelRepository
```
* Provide the Metamodel Repository implementation as a dependency in the classpath of the implementation.
