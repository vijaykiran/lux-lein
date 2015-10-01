# How to use it

You'll need a project.clj that imports the lux/lein plugin.

Here's an example:

```
(defproject lux/stdlib "0.3.0"
  :description "Standard library for the Lux family of programming languages."
  :url "https://github.com/LuxLang/stdlib"
  :license {:name "Mozilla Public License (Version 2.0)"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :plugins [[lux/lein "0.1.0"]]
  )

```

You'll also need the Lux compiler, which you can get from here:

	https://github.com/LuxLang/lux/releases/download/0.3.0/luxc.jar

Finally, you'll need to put your source code inside a `source` directory.

Now, all you need to do is run the plugin like this:

	lein luxc compile

To install Lux libraries inside your local repo, do this:

	lein luxc install

And to deploy to foreign repositories, just do:

	lein luxc deploy <repo-name>
	
e.g.
	
	lein luxc deploy clojars

