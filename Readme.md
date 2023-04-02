# Dependency Injection and friends

Problems:

- How to inject a correct dependency when coding to interfaces? (Dependency Inversion)
- How not to inject these dependencies manually? (remove a large manual initialization code for code with thousands of components)

Solutions:

- Dependency Injection (manual or automated by IoC Container) - inject dependencies
- Service Discovery (e.g. SPI) - find implementations for interfaces (or abstract classes)

> What is Dependency Inversion?
> 
> Upper layers do not  rely on lower layers (e.g. Database connectivity).
> Coding to interfaces and correct implementation is injected magically.
> What can be this magic?

We need to find a component and inject it.
Finding a correct component is "discovery".
Injecting a component is "injection".

IoC containers can do both, even though these are different things (SPI is discovery without an injection, 
Spring xml configuration is injection without discovery).

> IoC stands for Inversion of Control - don't call us, we will call you.
> That's a generic principle used in frameworks - code is written so that framework calls it.
> This is different from a library, where library methods are directly called.
> 
> IoC container stands for a Framework which may handle Dependency Injection automatically.
> Thus IoC container has a narrower meaning than IoC. 
> However Spring IoC has many more responsibilities than simple Dependency Injection.

Dependency Injection and Service discovery often come hand in hand, but they are not necessarily related.

Examples of Dependency Injection without Service Discovery (Muad'DI) 
and Service Discovery without Dependency Injection (SPI) follow.

## Dependency Injection

First let's clear what Dependency Injection is.

Not:

    public UserService() {
      this.userRepository = new UserRepositoryImpl();
    }

but:

    public UserService(UserRepositoryImpl userRepository) {
      this.userRepository = userRepository
    }

If constructor accepts `UserRepository` then it already becomes a dependency inversion.

Dependency injection can be done manually:

    UserResource userResource = new UserResource(new UserService());
    List<String> users = userResource.list();

But we use _DI frameworks_ (or _IoC containers_) to make injections for us. 

### DI frameworks

DI is special - it's the most important framework.
It's an uber-framework, because it instantiates everything else.

Java does not provide DI implementation out of the box.
It provides only plain constructors.
You don't have this:

    new UserResource(@WithInjected(UserService.class))

or similar.

But you also can't throw any DI library into the classpath and hope it will magically pick up all the `@Inject` annotations.
DI should be at the beginning, or nearly the beginning of the program and all the program should be executed within
the context of this DI.

Two main contexts of execution:

- Standard (Java SE, `public static void main`)
- Servlet (Java EE, `MyServlet extends HttpServlet`)

### Muad'DI

A sample implementation of a DI framework.

Extremely simple and limited, but still working DI.
Does not support interfaces (thus no named components);
does not support field or getter injection;
has only singleton scope etc.

No Null checks, no circular dependency checks.

Only to show what is DI under the hood.

Examples for both Java SE (`muaddi-core`) and Java EE (`muaddi-servlet`).

 - Tests in `muaddi-core` for Java SE
 - Deploy `muaddiexample.war` to Tomcat and call:

       http://localhost:8080/muaddiexample/books

Please note the handling of `MuadServletMapping` annotation in `MuadServlet`.
If we add some `MuadHandler` interface and find it by using SPI we can start doing something what Spring Framework does.

## Service Discovery

### SPI

Built-in service discovery in Java.

Example with JDBC drivers or Logging Implementations.

TODO: SPI example

## Discovery in DI frameworks

Service discovery may be built into the DI Frameworks.

Discovering the components can be done in different ways:

- Spring xml way - no autodiscovery, beans are injected as configured in an `.xml` file
- HK2 way - implementations are matched with interfaces manually, injected automatically
- Spring annotation way, Weld CDI way - everything is found automatically

## JCR 299 vs JCR 330

 - Simpler [JCR 330 Dependency Injection, DI](https://jakarta.ee/specifications/dependency-injection/1.0/)
 - Complex [JCR 299 Context And Dependency Injection, CDI](https://jakarta.ee/specifications/cdi/3.0/)

CDI depends on DI.

Most of the DI frameworks implement or follow the `JCR 330`.

`JCR 299` and its reference implementation _Weld CDI_ is about Java EE containers and such servers as WildFly or WebSphere.

## DI Framework examples

[Java SE examples](https://github.com/Col-E/Useful-Things/tree/master/tutorials/dependency-injection)

TODO: Spring, Guice and HK2 examples (maybe copy the `bigbang` project)

