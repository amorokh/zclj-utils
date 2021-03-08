(ns zclj-utils.core)

(defmacro while->
  "Repeatedly threads expr through the forms (via ->) while test is true, test should be
   a predicate that gets passed the threaded value"
  [test expr & forms]
  (let [g (gensym)
        steps (map (fn [step] `(if (~test ~g) (-> ~g ~step) ~g))
                   forms)]
    `(let [~g ~expr
           ~@(interleave (repeat g) (butlast steps))]
       ~(if (empty? steps)
          g
          (last steps)))))

(defmacro while->>
  "Repeatedly threads expr through the forms (via ->>) while test is true, test should be
   a predicate that gets passed the threaded value"
  [test expr & forms]
  (let [g (gensym)
        steps (map (fn [step] `(if (~test ~g) (->> ~g ~step) ~g))
                   forms)]
    `(let [~g ~expr
           ~@(interleave (repeat g) (butlast steps))]
       ~(if (empty? steps)
          g
          (last steps)))))
