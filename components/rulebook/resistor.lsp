(define-component 'resistor
  (('properties . (('resistance 'num "ohms")
    ('wattage 'num "watts")
    ('tolerance 'num "__")
    ('material (enum
      "I honestly have no idea"
      "More options"))))
  ('behaviour . ((output-voltage input-voltage)))))
    ;; The part about V = IR doesn't really belong in this file