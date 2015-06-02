package com.github.dnvriend.fp

import com.github.dnvriend.TestSpec

/**
 * This is a Scala adaptation from the example I took from
 * http://howtonode.org/what-is-this
 */
class LexicalScopeTest extends TestSpec {
  // It's all about where you are..

  // this is the global scope
  val globalName = "global"
  val globalAge = 15

  def localScope(): Unit = {
    val localName = "local"
    val localAge = 16
  }


  "global scope" should "show name and age" in {
    // this is a test function, and still we can reference globalName and globalAge,
    globalName shouldBe "global"
    globalAge shouldBe 15
  }

  "localScope" should "not be referenced" in {
    // commented the following two statements, the localName and localAge
    // variable cannot be referenced because they are local
//    localName shouldBe "local"
//    localAge shouldBe 16
  }

    /**
     * Lexical Scope
     *
     * Lexical scope is the key to making closures work. Here's a quote
     * from wikipedia about closures and lexical scope:
     *
     * In computer science, a closure is a first-class function
     * with `free variables`, that are bound in the lexical environment.
     * Such a function is said to be "closed over" its free variables.
     * A closure is defined within the scope of its free variables, and
     * the extent of those variables is at least as long as the
     * lifetime of the closure itself.
     *
     * So what does all that mean? Here's an example:
     */

  /**
   * The following method returns a function that returns a function that returns a String.
   * The function myModule contains the local variables name and age, and the
   * function () => String closes over those two variables and can be referenced from within
   * that function.
   */
    def myModule: () => () => String = () => {
      // lexical scope of the function 'greet'
      val name = "tim"
      val age = 28
      val greet: () => String = () => s"Hello $name. Wow, you are $age years old."
      greet
    }

  "closure 'greet'" should "reference the variables in its lexical scope" in {
    val greeter = myModule()
    greeter() shouldBe "Hello tim. Wow, you are 28 years old."
  }
}
