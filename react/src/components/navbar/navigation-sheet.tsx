import { Button } from '@/components/ui/button';
import { Sheet, SheetContent, SheetTrigger } from '@/components/ui/sheet';
import { Menu } from 'lucide-react';
import { NavMenu } from './nav-menu';
export const NavigationSheet = () => {
  return (
    <Sheet>
      <SheetTrigger asChild>
        <Button variant="outline" size="icon">
          <Menu />
        </Button>
      </SheetTrigger>
      <SheetContent>
        <div className="flex flex-col h-full">
          <div className="flex-1 overflow-y-auto">
            <NavMenu orientation="vertical" />
          </div>
        </div>
      </SheetContent>
    </Sheet>
  );
};
