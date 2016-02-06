organization := "com.github.fdietze"
name := "doddle"
version := "master-SNAPSHOT"

scalaVersion := "2.12.4"

enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)


resolvers += ("jitpack" at "https://jitpack.io")
libraryDependencies ++= (
  "com.github.fdietze" %%% "scala-js-orbit-db" % "master-SNAPSHOT" ::
  "io.github.outwatch" % "outwatch" % "5ef03ac" ::
  "com.github.fdietze" % "duality" % "9dd5e01649" ::
  "io.monix" %%% "minitest" % "2.0.0" % "test" ::
  Nil
)

testFrameworks += new TestFramework("minitest.runner.Framework")

scalaJSUseMainModuleInitializer := true
useYarn := true

addCommandAlias("dev", "; compile; fastOptJS::startWebpackDevServer; devwatch")
addCommandAlias("devwatch", "~; fastOptJS; copyFastOptJS")
webpackConfigFile in fastOptJS := Some(baseDirectory.value / "webpack.config.dev.js")
webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly()
webpackBundlingMode in fullOptJS := BundlingMode.Application
webpackDevServerPort := 12345
webpackDevServerExtraArgs := Seq("--progress", "--color")

lazy val copyFastOptJS = TaskKey[Unit]("copyFastOptJS", "Copy javascript files to target directory")
// this is a workaround for: https://github.com/scalacenter/scalajs-bundler/issues/180
copyFastOptJS := {
    val inDir = (crossTarget in (Compile, fastOptJS)).value
    val outDir = (crossTarget in (Compile, fastOptJS)).value / "fastopt"
    val files = Seq("doddle-fastopt-loader.js", "doddle-fastopt.js", "doddle-fastopt.js.map") map { p => (inDir / p, outDir / p) }
    IO.copy(files, overwrite = true, preserveLastModified = true, preserveExecutable = true)
}

scalacOptions ++=
    "-encoding" :: "UTF-8" ::
    "-unchecked" ::
    "-deprecation" ::
    "-explaintypes" ::
    "-feature" ::
    "-language:_" ::
    "-Xcheckinit" ::
    "-Xfuture" ::
    "-Xlint:-unused" ::
    "-Ypartial-unification" ::
    "-Yno-adapted-args" ::
    "-Ywarn-infer-any" ::
    "-Ywarn-value-discard" ::
    "-Ywarn-nullary-override" ::
    "-Ywarn-nullary-unit" ::
    "-P:scalajs:sjsDefinedByDefault" ::
    Nil
