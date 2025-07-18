import { Logo } from '../logo'
import { NavMenu } from './nav-menu'
import { NavigationSheet } from './navigation-sheet'

const Navbar = () => {

  return (
    <div className="bg-muted">
      <nav className="h-16 border-b bg-background">
        <div className="mx-auto flex h-full max-w-screen-xl items-center justify-between px-4 sm:px-6 lg:px-8">
          <div className="flex items-center">
            <Logo className="h-6 w-6 mr-2" />
            <NavMenu className="hidden md:block" />
          </div>
          <div className="flex items-center gap-3">
            <div className="md:hidden">
              <NavigationSheet />
            </div>
          </div>
        </div>
      </nav>
    </div>
  )
}
export default Navbar
