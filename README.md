# Rosanjin Talk (for smol boi)

An small game that consists of an ad-lib / Mad-Lib style substitution story game where you:
1. Write a story.
2. Define substitution questions.

And then the answers to the questions (e.g. noun, adjective, city) are
then substituted them into a story.

This is my first time using JavaFX in any serious capacity.

## Why is it called Rosanjin Talk?

Why wouldn't it be?

This is a bit of an inside joke that came about from:
* John Mulaney and the Sack Lunch Bunch's "Girl Talk" segment.
* The original Iron Chef's Rosanjin Scholar, Masaaki Hirano (平野雅章).
* Put these things together and you get Rosanjin Talk.

So the scripts created and used by this game are called `RosanjinTalk`s.

## Why is it for smol boi?

Because it was specifically written for my partner, who is both small and a boy
(inasmuch as a 30 something year old can be called a boy).

## How does it work?

It is written in Java to leverage JavaFX (although I suppose I could have written it in Kotlin or Scala)
and has two modes:

### `RosanjinTalk` creation mode

The creator mode to set up a list of substitution questions and to write a `RosanjinTalk` that allows for the
responses to the questions to be substituted into the story.

These are saved as simple serialized objects for convenience, despite Java serialization being discouraged in lieu
of JSON or XML.

(In this case, it seems easy enough to not be problematic.)

### `RosanjinTalk` play mode 

A `RosanjinTalk` serialized file is loaded and the player is prompted with the substitution questions.

The answers to said questions are then substituted into the story and the resultant story is displayed and can
be saved as a simple text file.

**Status**: Work in progress.

<!-- https://stackoverflow.com/questions/5258159/how-to-make-an-executable-jar-file -->
